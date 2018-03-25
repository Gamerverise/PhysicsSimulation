package PhysSim;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class Main extends Application {

    Simulation default_state = new Simulation();
    Simulation cur_state;

    Thread sim_thread;

    AnimationTimer anim_timer;
    GraphicsContext gc;

    Rectangle2D screen_bounds = Screen.getPrimary().getVisualBounds();

    Font size_30_font = new Font(30);

    ReentrantLock particles_pos_lock = new ReentrantLock();
    Semaphore sim_permit = new Semaphore(1, true);

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage stage) throws InterruptedException {
        double sun_volume = 4 / 3.0 * Math.PI * Math.pow(sun.radius, 3);

        double earth_volume = 4 / 3.0 * Math.PI * Math.pow(earth.radius, 3);

        particles = new Particle[]{sun, earth};

//        initial_zoom relative to the distance between the sun and earth (in units of px/m)
//        double initial_zoom = 0.5 - 0.1;
//        set_view_scale(sun, earth, initial_zoom, canvas_height);

        // initial_zoom relative to the diameter of the earth (in units of px/m)
        double initial_zoom = 0.005;
        set_view(sun, initial_zoom, canvas_height);

        sim_thread = new Thread(() -> {
            while (true) {
                try {
//                    if (time_step_counter > 1)
//                        continue;
                    sim_permit.acquire();
                    time_step();
                    sim_permit.release();
                    Thread.sleep((long)dt_real);
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
        Canvas canvas = new Canvas(canvas_width, canvas_height);

        root.getChildren().add(canvas);
        Scene scene = new Scene(root, canvas_width, canvas_height);
        stage.setScene(scene);

        gc = canvas.getGraphicsContext2D();

        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case SPACE:
                    try {
                        is_playing = !is_playing;

                        if (is_playing == true) {
                            sim_permit.release();
                            anim_timer.start();
                        } else {
                            sim_permit.acquire();
                            anim_timer.stop();
                            redraw();
                        }
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    break;
                case R:
                    restart();
            }
        });

        // Starting in the paused state
        sim_permit.acquire();
        // sim_thread will start paused because sim_permit is not available
        sim_thread.start();
        // Draw the screen once to see the initial state
        redraw();

        stage.setTitle("Physics Simulation");
        stage.setX(screen_bounds.getMaxX() - canvas_width);
        stage.setY(screen_bounds.getMinY());
        stage.setResizable(false);

        stage.show();
    }

    public void restart() {

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
        }

        particles_pos_lock.unlock();

        time_step_counter += 1;
    }
}

