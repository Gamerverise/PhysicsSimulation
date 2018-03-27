import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.concurrent.Semaphore;

// FIXME: Where does this comment go?
// Register screen coordinates and simulation coordinates

public class GameWidget {
    double init_view_origin_x;
    double init_view_origin_y;
    double init_view_scale;
    boolean init_is_playing;

    double view_origin_x;
    double view_origin_y;
    double view_scale;
    boolean is_playing;

    Canvas canvas;
    GraphicsContext gc;
    CameraView view;

    AnimationTimer anim_timer;
    Simulation simulation;

    Semaphore sim_permit = new Semaphore(1, true)

    public GameWidget(double view_origin_x, double view_origin_y, double view_scale,
                      Universe universe, double dt_real, double dt_sim, boolean is_playing)
    {
        init_view_origin_x = view_origin_x;
        init_view_origin_y = view_origin_y;
        init_view_scale    = view_scale;
        init_is_playing    = is_playing;

        // FIXME
        canvas = new Canvas(canvas_width, canvas_height);
        gc = new GraphicsContext(canvas);

        // FIXME
        double dist_earth_sun_px = canvas_width / 2 * 0.50;
        double dist_inset_px = 20;      // So the earth won't be at the exact edge
//    Show show = new Show(0, 0, view_scale, SolarSystem.stellar_objects, dt_real, dt_sim);
//        show.show();


        this.sim_permit.acquireUninterruptibly();
        this.is_playing = false;
        this.simulation = new Simulation(universe, dt_real, dt_sim, sim_permit);

        reset();
    }

    void toggle_play_pause() {
        is_playing = !is_playing;

        if (is_playing == true) {
            anim_timer.start();
            sim_permit.release();
        } else {
            sim_permit.acquireUninterruptibly();
            anim_timer.stop();
            redraw();       // FIXME: Why do we redraw here?
        }
    }

    void reset() {
        sim_permit.acquireUninterruptibly();
        is_playing = false;

        simulation.reset();

        view_origin_x = init_view_origin_x;
        view_origin_y = init_view_origin_y;
        view_scale    = init_view_scale;
        is_playing    = init_is_playing;

        // Draw the screen once to see the initial state
        redraw();

        anim_timer = new AnimationTimer() {
            public void handle(long now) {
                redraw();
            }
        };

        if (is_playing)
            toggle_play_pause();

//        initial_zoom relative to the distance between the sun and earth (in units of px/m)
//        double initial_zoom = 0.5 - 0.1;
//        set_view_scale(sun, earth, initial_zoom, canvas_height);

        // initial_zoom relative to the diameter of the earth (in units of px/m)
        double initial_zoom = 0.005;
        set_view(sun, initial_zoom, canvas_height);
    }

    public void redraw() {
//        gc.clearRect(0, 0, canvas_width, canvas_height);
        gc.clearRect(0, 0, 100, 100);
        gc.setFill(Color.BLACK);

        particles_pos_lock.lock();

        for (Particle p : particles)
            draw_particle(p, view.scale);

        particles_pos_lock.unlock();

        gc.setFill(Color.rgb(255, 0, 0, 0.5));
        gc.setFont(size_30_font);
        if (is_playing)
            gc.fillText("Playing", 5, 50);
        else
            gc.fillText("Paused", 5, 50);
    }

    void draw_particle(Particle p, double scale) {
        double scaled_radius = p.radius * scale;

        gc.setFill(p.color);

        if (p == earth) {
            gc.setFill(Color.rgb(0, 0, 255, 1));
            fill_circle((p.x - view.world_x) * scale + canvas_width / 2, (p.y - view.world_y) * scale + canvas_height / 2, 2);
            return;
        }

        fill_circle((p.x - view.world_x) * scale + canvas_width / 2, (p.y - view.world_y) * scale + canvas_height / 2, scaled_radius);
    }
}
