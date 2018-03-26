import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

// FIXME: Where does this comment go?
// Register screen coordinates and simulation coordinates

public class GameWidget {

    double dist_earth_sun_px = canvas_width / 2 * 0.50;
    double dist_inset_px = 20;      // So the earth won't be at the exact edge

    Simulation simulation;
    CameraView view;
    GraphicsContext gc;
    AnimationTimer anim_timer;

//    Show show = new Show(0, 0, view_scale, SolarSystem.stellar_objects, dt_real, dt_sim);
//        show.show();

    public GameWidget(double view_origin_x, double view_origin_y, double view_scale,
                      Universe universe, double dt_real, double dt_sim)
    {
        simulation = new Simulation(universe, dt_real, dt_sim);

        // Starting in the paused state
        simulation.sim_permit.acquire();

        // Thread will start paused because sim_permit is not available
        simulation.thread.start();

        anim_timer = new AnimationTimer() {
            public void handle(long now) {
                redraw();
            }
        };

        // Draw the screen once to see the initial state
        redraw();

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

    void fill_circle(double x, double y, double radius) {
        // The location is offset by the radius because for this function (x, y) specify
        // the center, but for fillOval (x, y) specifies the top-left of the bounding box
        // of the oval
        gc.fillOval(x - radius / 2, y - radius / 2, radius, radius);
    }
}
