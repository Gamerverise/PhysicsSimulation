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
    double dt = 1;   // time step in ms

    double canvas_width = 800;
    double scale;
    double x_offset;
    double y_offset;

    // register your screen coordinates and your simulation coordinates
    // constant of proportionality for the masses

    public static void main(String[] args) {
        launch(args);
    }

    Particle sun = new Particle(0, 0, 0, 0, 0, 0, 1.989e30, 6.957e8, 0);
    Particle earth = new Particle(1.496e11, 0, 0, 3e4, 0, 0, 5.972e24, 6.371e6, 0);

    @Override
    public void start(Stage stage) throws InterruptedException {
        double earth_acc = G * sun.mass / earth.x / earth.x;
        earth.ax = -earth_acc;

        double sun_volume = 4 / 3.0 * Math.PI * Math.pow(sun.radius, 3);
        sun.density = sun.mass / sun_volume;

        double earth_volume = 4 / 3.0 * Math.PI * Math.pow(earth.radius, 3);
        earth.density = earth.mass / earth_volume;

        particles = new Particle[]{sun, earth};

        sim_thread = new Thread(() -> {
            while (true) {
                time_step();
                try {
                    Thread.sleep((long)dt);
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
        Canvas canvas = new Canvas(canvas_width, canvas_width / 2);

        root.getChildren().add(canvas);
        Scene scene = new Scene(root, 800, 400);
        stage.setScene(scene);

        gc = canvas.getGraphicsContext2D();

        stage.setTitle("Physics Simulation");
        stage.setResizable(false);

        stage.show();

        sim_thread.start();
        anim_timer.start();
    }

   public void redraw() {
//        gc.clearRect(0, 0, 800, 400);

        particles_pos_lock.lock();

        for (Particle p : particles)
            draw_particle(p, (canvas_width / 2 - 20) / earth.radius, canvas_width / 2);

        particles_pos_lock.unlock();
    }

    public void time_step() {
        for (int i = 0; i < particles.length; i++) {
            for (int j = i + 1; j < particles.length; j++) {
                Particle pi = particles[i];
                Particle pj = particles[j];

                double dist = pi.distance(pj);
                double acc = G * pj.mass / dist / dist;

                double dir_pi_pj_x = pj.x - pi.x; // x-component of the (pi -> pj) vector
                double dir_pi_pj_y = pj.y - pi.y; // y-component of the (pi -> pj) vector

                double ux = dir_pi_pj_x / dist;   // x-component of the unit vector in the direction (pi -> pj)
                double uy = dir_pi_pj_y / dist;   // y-component of the unit vector in the direction (pi -> pj)

                double ax = acc * ux;             // x-component of the acceleration times the unit vector
                double ay = acc * uy;             // y-component of the acceleration times the unit vector

                pi.ax += ax;
                pi.ay += ay;

                pj.ax += -ax;
                pj.ay += -ay;
            }
        }

        particles_pos_lock.lock();

        for (int i = 0; i < particles.length; i++) {
            Particle p = particles[i];
            p.vx += p.ax * dt;
            p.vy += p.ay * dt;
            p.x += p.vx * dt;
            p.y += p.vy * dt;
        }

        particles_pos_lock.unlock();
    }

    void draw_particle(Particle p, double scale, double x_offset, double y_offset) {
        gc.setFill(Color.rgb(0, 0, 255, 0.5));
        double radius = p.mass * scale;
        gc.fillOval(p.x * scale + x_offset, p.y * scale + y_offset, mass, mass);
    }
}

class Particle {
    double x;
    double y;
    double vx;
    double vy;
    double ax;
    double ay;
    double mass;
    double radius;
    double density;

    Particle(double x, double y, double vx, double vy, double ax, double ay, double mass, double radius, double density) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.ax = ax;
        this.ay = ay;
        this.mass = mass;
        this.radius = radius;
        this.density = density;
    }

    double distance(Particle p) {
        double dx = p.x - x;
        double dy = p.y - y;
        return Math.sqrt(dx*dx + dy*dy);
    }
}
