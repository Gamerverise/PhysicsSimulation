package lib.graphics;

import javafx.scene.canvas.GraphicsContext;
import lib.java_api_extensions.MathX;
import lib.javafx_api_extensions.GraphicsContextX;

import java.awt.geom.Point2D;

import static lib.java_api_extensions.MathX.atan_vector;

public class CustomGraphicsContext extends GraphicsContextX {

    public CustomGraphicsContext(GraphicsContext gc) {
        super(gc);
    }

    public void draw_arrow(double x, double y, double magnitude_x, double magnitude_y) {
        double fin_angle_degrees = 10;
        double fin_length_factor = 0.10;

        double fin_x = 1 - fin_length_factor * MathX.cos(fin_angle_degrees);
        double fin_dy = fin_length_factor * MathX.sin(fin_angle_degrees);

        double arrow_angle;

        try {
            arrow_angle = atan_vector(magnitude_x, magnitude_y);
        } catch (ArithmeticException e) {
            return;
        }

        double scale = Point2D.distance(0, 0, magnitude_x, magnitude_y);

        gc.save();

        gc.translate(x, y);
        gc.scale(scale, scale);
        gc.rotate(arrow_angle);

        gc.moveTo(0, 0);
        gc.lineTo(1, 0);

        gc.lineTo(fin_x, -fin_dy);
        gc.moveTo(    1,       0);
        gc.lineTo(fin_x,  fin_dy);

        gc.restore();
    }
}
