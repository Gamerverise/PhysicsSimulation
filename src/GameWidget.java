import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.concurrent.locks.ReentrantLock;

// FIXME: Where does this comment go?
// Register screen coordinates and simulation coordinates

public class GameWidget extends Canvas {
    GraphicsContextX gcx;
    GraphicsContext gc;

    ReentrantLock play_pause_lock;
    AnimationTimer anim_timer;

    boolean init_is_playing;

    Simulation simulation;
    boolean is_playing;

    // FIXME
    double min_particle_radius;

    public GameWidget(double width, double height,
                      Universe universe,
                      double dt_real,
                      double dt_sim,
                      boolean is_playing)
    {
        this(width, height,
             new Simulation(universe, dt_real, dt_sim),
             is_playing);
    }

    public GameWidget(double width, double height,
                      Simulation simulation,
                      boolean is_playing)
    {
        super(width, height);

        init_is_playing = is_playing;

        gc = getGraphicsContext2D();
        gcx = new GraphicsContextX(gc);

        gc.setFont(new Font(30));
        gc.setFill(Color.rgb(255, 0, 0, 0.5));

        // Set origin is center of canvas, instead of top left
        gc.translate(getWidth() / 2, getHeight() / 2);

        // Change positive y direction from down to up
        gc.scale(1, -1);

/////////////

//        initial_zoom relative to the distance between the sun and earth (in units of px/m)
//        double initial_zoom = 0.5 - 0.1;
//        set_view_scale(sun, earth, initial_zoom, canvas_height);

        // initial_zoom relative to the diameter of the earth (in units of px/m)
        double initial_zoom = 0.005;
        set_view(sun, initial_zoom, canvas_height);


//////////////////////////////////////
        // FIXME
        double dist_earth_sun_px = canvas_width / 2 * 0.50;
        double dist_inset_px = 20;      // So the earth won't be at the exact edge
//    Show show = new Show(0, 0, view_scale, SolarSystem.stellar_objects, dt_real, dt_sim);
//        show.show();


///////////        ///////////////////////

        play_pause_lock = new ReentrantLock();

        this.simulation = simulation;
        this.simulation.play_pause_lock = play_pause_lock;

        reset();
    }

    void reset() {

        play_pause_lock.lock();

        anim_timer.stop();

        simulation.reset();
        is_playing = init_is_playing;

        // Draw the screen once to see the initial state
        redraw();

        anim_timer = new AnimationTimer() {
            public void handle(long now) {
                redraw();
            }
        };

        if (is_playing)
            toggle_play_pause();

        simulation.start_once(play_pause_lock);
    }

    void toggle_play_pause() {
        is_playing = !is_playing;

        if (is_playing == true) {
            anim_timer.start();
            play_pause_lock.unlock();
        } else {
            play_pause_lock.lock();
            anim_timer.stop();
            // redraw();       // FIXME: Why did we redraw here?
        }
    }

    // view_particle
    //     Change the current view (aka transform) so that:
    //
    //         (center_px, center_py) = (canvas_origin_x, canvas_origin_x)
    //     and
    //         diameter_px(p, q) = zoom * canvas_dim

    public void view_particle(Particle p, double zoom, Misc.Dimension dim) {
        double scale;

        gc.translate(p.x, p.y);

        if (dim == Misc.Dimension.WIDTH)
            scale = getWidth() / 2 / p.radius * zoom;
        else
            scale = getHeight() / 2 / p.radius * zoom;

        gc.scale(scale, scale);
    }

    // view_particle
    //     Scale the current view (aka transform) so that:
    //
    //         dist_px(p, q) = zoom * canvas_dim

    public void view_particles(Particle p, Particle q, double zoom, Misc.Dimension dim) {
        double scale;

        if (dim == Misc.Dimension.WIDTH)
            scale = getWidth() / p.distance(q) * zoom;
        else
            scale = getHeight() / p.distance(q) * zoom;

        gc.scale(scale, scale);
    }

    public void set_min_radius() {

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
