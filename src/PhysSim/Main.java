package PhysSim;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;

public class Main extends Application {

    Particle[] particles;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Drawing Operations Test");
        Group root = new Group();
        Canvas canvas = new Canvas(800, 400);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        drawShapes(gc);
        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        particles = new Particle[]
                {
                        new Particle(400, 200, 4, 10, 0, 0, 80),
                        new Particle(263, 654, 11, 6, 0, 0, 60),
                        new Particle(652, 352, -32, -8, 0, 0, 40)
                };
    }

    double G = 0.001;   // Pretend gravitation constant
    double dt = 0.01;   // Time step

    private void time_step() {
        while (true) {
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

            for (int i = 0; i < particles.length; i++) {
                Particle pi = particles[i];
                pi.vx += pi.ax * dt;
                pi.vy += pi.ay * dt;
                pi.x += pi.vx * dt;
                pi.y += pi.vy * dt;

                // update image
            }
        }
    }

    private void drawShapes(GraphicsContext gc) {
        gc.setFill(Color.GREEN);
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(5);
        gc.strokeLine(40, 10, 10, 40);
        gc.fillOval(10, 60, 30, 30);
        gc.strokeOval(60, 60, 30, 30);
        gc.fillRoundRect(110, 60, 30, 30, 10, 10);
        gc.strokeRoundRect(160, 60, 30, 30, 10, 10);
        gc.fillArc(10, 110, 30, 30, 45, 240, ArcType.OPEN);
        gc.fillArc(60, 110, 30, 30, 45, 240, ArcType.CHORD);
        gc.fillArc(110, 110, 30, 30, 45, 240, ArcType.ROUND);
        gc.strokeArc(10, 160, 30, 30, 45, 240, ArcType.OPEN);
        gc.strokeArc(60, 160, 30, 30, 45, 240, ArcType.CHORD);
        gc.strokeArc(110, 160, 30, 30, 45, 240, ArcType.ROUND);
        gc.fillPolygon(new double[]{10, 40, 10, 40},
                new double[]{210, 210, 240, 240}, 4);
        gc.strokePolygon(new double[]{60, 90, 60, 90},
                new double[]{210, 210, 240, 240}, 4);
        gc.strokePolyline(new double[]{110, 140, 110, 140},
                new double[]{210, 210, 240, 240}, 4);
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

    Particle(double x, double y, double vx, double vy, double ax, double ay, double mass) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.ax = ax;
        this.ay = ay;
        this.mass = mass;
    }

    double distance(Particle p) {
        double dx = p.x - x;
        double dy = p.y - y;
        return Math.sqrt(dx*dx - dy*dy);
    }
}
