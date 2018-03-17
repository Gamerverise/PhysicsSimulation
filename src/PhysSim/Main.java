package PhysSim;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.concurrent.locks.ReentrantLock;

public class Main extends Application {

    Particle[] particles;
    Thread sim_thread;

    AnimationTimer anim_timer;
    GraphicsContext gc;

    ReentrantLock particles_pos_lock = new ReentrantLock();

    double G = 6.67408e-11;    // pretend gravitation constant
    double c = 299_792_458;    // speed of light in m/s
    double max_speed = c - 1;    // naive max speed

    public static void main(String[] args) {
        launch(args);
    }

    // Original setup, not working
    Particle sun = new Particle("sun", 0, 0, 0, 0, 0, 0, 1.989e30, 6.957e8, 0, Color.rgb(0, 0, 0, 1));
    Particle earth = new Particle("earth", 1.496e11, 0, 0, 3e4, 0, 0, 5.972e24, 6.371e6, 0, Color.rgb(0, 0, 0, 1));

    // Symmetry of original setup about x = y
//    Particle sun = new Particle("sun", 0, 0, 0, 0, 0, 0, 1.989e30, 6.957e8, 0, Color.rgb(0, 0, 0, 1));
//    Particle earth = new Particle("earth", 0, 1.496e11, 3e4, 0, 0, 0, 5.972e24, 6.371e6, 0, Color.rgb(0, 0, 0, 1));

    // FIXME: Not sure if this is correct
//    // Original setup, working with speed change
//    Particle sun = new Particle("sun", 0, 0, 0, 0, 0, 0, 1.989e30, 6.957e8, 0, Color.rgb(0, 0, 0, 1));
//    Particle earth = new Particle("earth", 1.496e11, 0, 0, 1.485e11, 0, 0, 5.972e24, 6.371e6, 0, Color.rgb(0, 0, 0, 1));

    // FIXME: Not sure if this is correct
//    // Symmetry of original setup about x = y, working with speed change
//    Particle sun = new Particle("sun", 0, 0, 0, 0, 0, 0, 1.989e30, 6.957e8, 0, Color.rgb(0, 0, 0, 1));
//    Particle earth = new Particle("earth", 0, 1.496e11, 1.485e11, 0, 0, 0, 5.972e24, 6.371e6, 0, Color.rgb(0, 0, 0, 1));

    // FIXME: Not sure if this is correct
//    double earth_x = Math.sqrt(1.496e11 * 1.496e11 / 2);
//    double earth_speed = Math.sqrt(3e4 * 3e4 / 2);
//
//    // Another symmetry of original setup, working with speed change
//
//    Particle sun = new Particle("sun", 0, 0, 0, 0, 0, 0, 1.989e30, 6.957e8, 0, Color.rgb(0, 0, 0, 1));
//    Particle earth = new Particle("earth", earth_x, earth_x, earth_speed, earth_speed, 0, 0, 5.972e24, 6.371e6, 0, Color.rgb(0, 0, 0, 1));

    // register screen coordinates and simulation coordinates

    double canvas_width = 1800;
    double canvas_height = 1000;
    double canvas_aspect_ratio = canvas_width / canvas_height;

    double dist_earth_sun_px = canvas_width / 2 * 0.50;
    double dist_inset_px = 20;      // So the earth won't be at the exact edge


    View view = new View();

    // time_scale_real_per_sim (ms [rea] / s [sim]) = elapsed_time_real (ms) / elapsed_time_sim (s)
    //
    // because Thread.sleep takes ms, and our math simulation is based on seconds. So,
    //
    // time_scale_real_per_sim (ms/s)
    //   = time_scale_real_per_sim (min [real] / yr [sim]) * 60 s/min  * 1000 ms/s * 1/365 yr/d * 1/24 d/hr * 1/60 h/min * 1/60 min/s
    //   = time_scale_real_per_sim (min [real] / yr [sim]) * 1000 ms/s * 1/365 yr/d * 1/24 d/hr * 1/60 h/min

    double time_scale_real_per_sim = 1 * 1000.0 / 365.0 / 24.0 / 60.0;       // scale (ms/s) for 1 min / 1 yr
    double time_scale_sim_per_real = 1 / time_scale_real_per_sim;    // scale (s/ms) for 1 yr / 1 min

    double dt_real = 1;     // (ms)
//    double dt_sim = dt_real * time_scale_sim_per_real;     // (s)
    double dt_sim = 60*60*10;     // (s)

    // Which corresponds to dt_sim as follows:
    //
    // dt_sim = dt_real / scale     // (s/ms)
    //        = 1 ms /



    @Override
    public void start(Stage stage) throws InterruptedException {
        double sun_volume = 4 / 3.0 * Math.PI * Math.pow(sun.radius, 3);
        sun.density = sun.mass / sun_volume;

        double earth_volume = 4 / 3.0 * Math.PI * Math.pow(earth.radius, 3);
        earth.density = earth.mass / earth_volume;

        particles = new Particle[]{sun, earth};

//        double scalet = 1e8;
//        scalet = 1e7;
//        scalet = 5e7;
//        scalet = 2.5e7;
//
//        fill_circle((p.x - 1.496e11) / scalet + canvas_width / 2, p.y / scalet + canvas_height / 2, 2);

        // initial_zoom relative to the distance between the sun and earth (in units of px/m)
//        double initial_zoom = 0.5 - 0.1;
//        set_view_scale(sun, earth, initial_zoom, canvas_height);

        // initial_zoom relative to the diameter of the earth (in units of px/m)
        double initial_zoom = 0.1;
        set_view(earth, initial_zoom, canvas_height);

        sim_thread = new Thread(() -> {
            while (true) {
                time_step();
                try {
                    Thread.sleep((long)(dt_real));
                } catch (InterruptedException e) {
                    System.out.println("SimulationThread.run: InterruptedException");
                }
            }
        });

        anim_timer = new AnimationTimer() {
            public void handle(long now) {
                redraw();
            }
        };

        Group root = new Group();
        Canvas canvas = new Canvas(canvas_width, canvas_width);

        root.getChildren().add(canvas);
        Scene scene = new Scene(root, canvas_width, canvas_width);
        stage.setScene(scene);

        gc = canvas.getGraphicsContext2D();

        stage.setTitle("Physics Simulation");
        stage.setResizable(false);

        stage.show();

        sim_thread.start();
        anim_timer.start();
    }

    // set_view is for viewing a particle:
    //     * the world coordinates of the view are set to the particles coordinates, and
    //     * and the view's scale is zoomed relative to the particle's diameter
    //
    // zoom of 1 means that the particle's diameter (in px) equals the canvas's height

    public void set_view(Particle p, double zoom, double canvas_height) {
        view.set_view(p.x, p.y, canvas_height / 2 / p.radius * zoom);
    }

    // set_view_scale is for zooming the view's scale relative to the distance between two particles
    //
    // zoom of 1 means that the distance (in px) between the two particles equals the canvas's height

    public void set_view_scale(Particle p, Particle q, double zoom, double canvas_height) {
        view.scale = canvas_height / p.distance(q) * zoom;
    }

    public void redraw() {
//       gc.clearRect(0, 0, canvas_width, canvas_height);
       gc.setFill(Color.BLACK);
       gc.fillRect(0, 0, 10, 10);

        particles_pos_lock.lock();

        for (Particle p : particles)
            draw_particle(p, view.scale);

        particles_pos_lock.unlock();
    }

    public void time_step() {
        for (int i = 0; i < particles.length; i++) {
            for (int j = i + 1; j < particles.length; j++) {
                Particle pi = particles[i];
                Particle pj = particles[j];

                double dist = pi.distance(pj);

                double dir_pi_pj_x = pj.x - pi.x; // x-component of the (pi -> pj) vector
                double dir_pi_pj_y = pj.y - pi.y; // y-component of the (pi -> pj) vector

                double ux = dir_pi_pj_x / dist;   // x-component of the unit vector in the direction (pi -> pj)
                double uy = dir_pi_pj_y / dist;   // y-component of the unit vector in the direction (pi -> pj)

                // By physics,
                //
                // force = G * m1 * m2 / dist^2
                //
                // acc_m1 = force / m1 = G * m2 / dist^2

                double acc_factor = G / dist / dist;

                double acc_pi = acc_factor * pj.mass;
                pi.ax = acc_pi * ux;             // x-component of the acceleration times the unit vector
                pi.ay = acc_pi * uy;             // y-component of the acceleration times the unit vector

                double acc_pj = -acc_factor * pi.mass;
                pj.ax = acc_pj * ux;             // x-component of the acceleration times the unit vector
                pj.ay = acc_pj * uy;             // y-component of the acceleration times the unit vector
            }
        }

        particles_pos_lock.lock();

        for (int i = 0; i < particles.length; i++) {
            Particle p = particles[i];
            p.vx += p.ax * dt_sim;
            p.vy += p.ay * dt_sim;

            double new_speed = Math.sqrt(p.vx * p.vx + p.vy * p.vy);

            if (new_speed > max_speed) {
                p.vx = p.vx / new_speed * max_speed;
                p.vy = p.vy / new_speed * max_speed;
            }

            p.x += p.vx * dt_sim;
            p.y += p.vy * dt_sim;
            int fkdj= 6;                // FIXME
        }

        particles_pos_lock.unlock();
    }

    void draw_particle(Particle p, double scale) {
        double scaled_radius = p.radius * scale;

        gc.setFill(p.color);

        if (p == earth) {
            gc.setFill(Color.rgb(0, 0, 255, 1));
            fill_circle((p.x + view.world_x) * scale + canvas_width / 2, (p.y + view.world_y) * scale + canvas_height / 2, 2);
            return;
        }

        fill_circle((p.x + view.world_x) * scale + canvas_width / 2, (p.y + view.world_y) * scale + canvas_height / 2, scaled_radius);
    }

    void fill_circle(double x, double y, double radius) {
        // The location is offset by the radius because for this function (x, y) specify
        // the center, but for fillOval (x, y) specifies the top-left of the bounding box
        // of the oval
        gc.fillOval(x - radius / 2, y - radius / 2, radius, radius);
    }
}

class View {
    double world_x;
    double world_y;
    double scale;

    View() {
        world_x = 0;
        world_y = 0;
        scale = 1;
    }

    View(double x, double y, double scale) {
        world_x = x;
        world_y = y;
        this.scale = scale;
    }

    void set_view(double x, double y, double scale) {
        world_x = x;
        world_y = y;
        this.scale = scale;
    }
}

class Particle {
    String name;
    double x;
    double y;
    double vx;
    double vy;
    double ax;
    double ay;
    double mass;
    double radius;
    double density;
    Color color;

    Particle(String name, double x, double y, double vx, double vy, double ax, double ay, double mass, double radius, double density, Color color) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.ax = ax;
        this.ay = ay;
        this.mass = mass;
        this.radius = radius;
        this.density = density;
        this.color = color;
    }

    double distance(Particle p) {
        double dx = p.x - x;
        double dy = p.y - y;
        return Math.sqrt(dx*dx + dy*dy);
    }
}
