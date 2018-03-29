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

    Simulation init_simulation;
    boolean init_is_playing;

    Simulation simulation;
    boolean is_playing;

    double min_drawing_radius;

    public GameWidget(double width, double height) {
        super(width, height);
    }

    public void finish_construction(Universe universe,
                                    double dt_real,
                                    double dt_sim,
                                    boolean is_playing,
                                    GameView gv)
    {
        Simulation sim = new Simulation(universe, dt_real, dt_sim);
        finish_construction(sim, is_playing, gv);
    }

    public void finish_construction(Simulation simulation, boolean is_playing, GameView gv)
    {
        init_simulation = simulation;
        init_is_playing = is_playing;

        gc = getGraphicsContext2D();
        gcx = new GraphicsContextX(gc);

        gc.setFont(new Font(30));
        gc.setFill(Color.rgb(255, 0, 0, 0.5));

        // Set origin to center of canvas, instead of top left
        gc.translate(getWidth() / 2, getHeight() / 2);

        // Change positive y direction from down to up
        gc.scale(1, -1);

        if (gv != null)
            gv.change_view(this);

        shared_construction();

        set_min_drawing_radius();

        // FIXME: first_run() reorg
    }

    void shared_construction() {
        anim_timer = new AnimationTimer() {
            public void handle(long now) {
                redraw();
            }
        };

        this.simulation = new Simulation(init_simulation, Misc.CopyType.DEEP);
        is_playing = init_is_playing;
    }

    void run() {
        if (is_playing)
            toggle_run_suspend();
        else
            // Draw the screen once to see the initial state
            redraw();
    }

    void reset() {
        anim_timer.stop();
        shared_construction();
        run();
    }

    void toggle_run_suspend() {
        is_playing = !is_playing;

        if (is_playing == true) {
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

        if (is_playing)
            gc.fillText("Playing", 5, 50);
        else
            gc.fillText("Paused", 5, 50);
    }
}
