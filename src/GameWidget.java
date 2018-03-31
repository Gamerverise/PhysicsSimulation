import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.transform.NonInvertibleTransformException;

public class GameWidget extends Canvas {
    GraphicsContextX gcx;
    GraphicsContext gc;

    AnimationTimer anim_timer;

    GameView init_gv;

    SimulationStatic init_simulation;
    boolean init_is_running;

    SimulationDynamic simulation;
    boolean is_running;

    double min_drawing_radius;

    public GameWidget(double width, double height,
                      GameView init_gv,
                      Universe universe,
                      double dt_real,
                      double dt_sim,
                      boolean is_running)
    {
        super(width, height);
        finish_construction(init_gv, universe, dt_real, dt_sim, is_running);
    }

    public GameWidget(double width, double height,
                      GameView init_gv,
                      SimulationStatic sim,
                      boolean is_running)
    {
        super(width, height);
        finish_construction(init_gv, sim, is_running);
    }

    public void finish_construction(GameView init_gv,
                                    Universe universe,
                                    double dt_real,
                                    double dt_sim,
                                    boolean is_running)
    {
        finish_construction(init_gv, new SimulationStatic(universe, dt_real, dt_sim), is_running);
    }

    public void finish_construction(GameView init_gv, SimulationStatic sim, boolean is_running) {
        this.init_gv = init_gv;

        init_simulation = sim;
        init_is_running = is_running;

        this.simulation = new SimulationDynamic(init_simulation, Misc.CopyType.DEEP);
        this.is_running = init_is_running;
    }

    void init_graphics_context() {
        gc = getGraphicsContext2D();
        gcx = new GraphicsContextX(gc);

        gc.setFont(new Font(30));
        gc.setFill(Color.rgb(255, 0, 0, 0.5));

        // Set origin to center of canvas, instead of top left
        gc.translate(getWidth() / 2, getHeight() / 2);

        // Change positive y direction from down to up
        gc.scale(1, -1);

        if (init_gv != null)
            init_gv.change_view(this);

        set_min_drawing_radius();

        anim_timer = new AnimationTimer() {
            public void handle(long now) {
                redraw();
            }
        };
    }

    void init_run() {
        init_graphics_context();
        run();
    }
    
    void reset() {
        anim_timer.stop();

        simulation.reset(simulation);
        is_running = init_is_running;
        
        init_run();
    }

    void run() {
        if (is_running)
            toggle_run_suspend();
        else
            // Draw the screen once to see the initial state
            redraw();
    }

    void toggle_run_suspend() {
        is_running = !is_running;

        if (is_running == true) {
            anim_timer.start();
            simulation.run();
        } else {
            simulation.suspend();
            anim_timer.stop();
        }
    }

    double get_dimension(Misc.Dimension dim) {
        switch (dim) {
            case WIDTH:
                return getWidth();
            case HEIGHT:
                return getHeight();
            case MAX:
                if (getWidth() > getHeight())
                    return getWidth();
                else
                    return getHeight();
            default:
                assert false : "GameWidget.get_dimension: " + Debug.BAD_CODE_PATH;
                return 0;
        }
    }

    public void set_view(double x, double y, double scale) {
        gc.translate(x, y);
        gc.scale(scale, scale);
    }

    // view_particle
    //     Change the current view (aka transform) so that:
    //
    //         (center_px, center_py) = (canvas_origin_x, canvas_origin_x)
    //     and
    //         diameter_px(p, q) = zoom * canvas_dim

    public void view_particle(Particle p, double zoom, Misc.Dimension dim) {
        set_view(p.x, p.y, get_dimension(dim) / 2 / p.radius * zoom);
    }

    // view_particle
    //     Scale the current view (aka transform) so that:
    //
    //         dist_px(p, q) = zoom * canvas_dim

    public void view_particles(Particle p, Particle q, double zoom, Misc.Dimension dim) {
        double scale = get_dimension(dim) / p.distance(q) * zoom;
        gc.scale(scale, scale);
    }

    public void set_min_drawing_radius() {
        try {
            min_drawing_radius = gc.getTransform().inverseTransform(2.1, 0).getX();
        } catch (NonInvertibleTransformException e) {
            assert false : "GameWidget.set_min_radius: " + Debug.BAD_CODE_PATH;
        }
    }

    void draw_particle(Particle p) {
        gc.setFill(p.color);
        gcx.fill_circle(p.x, p.y, p.radius);
    }

    public void redraw() {
//        gc.clearRect(0, 0, canvas_width, canvas_height);
//        gc.setFill(Color.BLACK);

        gc.clearRect(0, 0, 100, 100);

        simulation.xy_data_rw_lock.lock();

        for (Particle p : simulation.universe.particles)
            draw_particle(p);

        simulation.xy_data_rw_lock.unlock();

        if (is_running)
            gc.fillText("Playing", 5, 50);
        else
            gc.fillText("Paused", 5, 50);
    }
}
