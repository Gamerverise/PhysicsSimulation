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

    double min_radius_px = 1.1;

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
            view(init_gv);

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

    void scale_x(double scale, double base_pixels) {
        try {
            gc.scale(scale, scale);
            gcx.min_radius = Math.abs(gc.getTransform().inverseTransform(scale * min_radius_px / base_pixels, 0).getX());
        } catch (NonInvertibleTransformException e) {
            assert false : "GameWidget.scale_x: " + Debug.BAD_CODE_PATH;
        }
    }

    void scale_y(double scale, double base_pixels) {
        try {
            gc.scale(scale, scale);
            gcx.min_radius = Math.abs(gc.getTransform().inverseTransform(0, scale * min_radius_px / base_pixels).getY());
        } catch (NonInvertibleTransformException e) {
            assert false : "GameWidget.scale_y: " + Debug.BAD_CODE_PATH;
        }
    }

    void scale(double scale, Misc.Dimension dim) {
        switch (dim) {
            case WIDTH:
                scale_x(scale, getWidth());
                break;
            case HEIGHT:
                scale_y(scale, getHeight());
                break;
            case MAX:
                if (getWidth() > getHeight())
                    scale_x(scale, getWidth());
                else
                    scale_y(scale, getHeight());
                break;
            case HALF_WIDTH:
                scale_x(scale, getWidth()/2);
                break;
            case HALF_HEIGHT:
                scale_y(scale, getHeight()/2);
                break;
            case HALF_MAX:
                if (getWidth() > getHeight())
                    scale_x(scale, getWidth()/2);
                else
                    scale_y(scale, getHeight()/2);
                break;
            default:
                assert false : "GameWidget.scale: " + Debug.BAD_CODE_PATH;
        }
    }

    // view_particle
    //     Change the current view (aka transform) so that:
    //
    //         (center_px, center_py) = (canvas_origin_x, canvas_origin_x)
    //     and
    //         diameter_px(p, q) = zoom * canvas_dim

    public void view_particle(Particle p, double zoom, Misc.Dimension dim) {
        double scale = 1 / p.radius / 2 * zoom;
        gc.translate(p.x, p.y);
        scale(scale, dim);
    }

    // FIXME: Old comment
    // view_particle
    //     Scale the current view (aka transform) so that:
    //
    //         dist_px(p, q) = zoom * canvas_dim

    public void view_two_particles(Particle center, Particle p, Particle q, double zoom, Misc.Dimension dim) {
        double scale = 1 / p.distance(q) * zoom;
        gc.translate(center.x, center.y);
        scale(scale, dim);
    }

    public void view(GameView gv) {
        if (gv instanceof GameViewParticle) {
            GameViewParticle v = (GameViewParticle) gv;
            view_particle(v.p, v.zoom, v.dim);
        } else if (gv instanceof GameViewTwoParticles) {
            GameViewTwoParticles v = (GameViewTwoParticles) gv;
            view_two_particles(v.center, v.p, v.q, v.zoom, v.dim);
        } else
            assert false : "GameWidget.view: " + Debug.BAD_CODE_PATH;
    }

    public void view(GameViewParticle gv) {
        view_particle(gv.p, gv.zoom, gv.dim);
    }

    public void view(GameViewTwoParticles gv) {
        view_two_particles(gv.center, gv.p, gv.q, gv.zoom, gv.dim);
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
