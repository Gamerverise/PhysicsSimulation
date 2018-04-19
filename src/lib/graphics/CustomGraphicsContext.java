package lib.graphics;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import lib.geometry.Geometry;
import lib.java_api_extensions.MathX;
import lib.javafx_api_extensions.GraphicsContextX;

import java.awt.geom.Point2D;

public class CustomGraphicsContext extends GraphicsContextX {

    public CustomGraphicsContext(GraphicsContext gc) {
        super(gc);
    }

    public void draw_arrow(double x1, double y1, double x2, double y2, Color color) {
        double fin_angle_degrees = 10;
        double fin_length_factor = 0.10;

        double fin_x = 1 - fin_length_factor * MathX.cos(fin_angle_degrees);
        double fin_dy = fin_length_factor * MathX.sin(fin_angle_degrees);

        double arrow_angle;

        try {
            arrow_angle = Geometry.line_angle_degrees(x1, y1, x2, y1);
        } catch (ArithmeticException e) {
            return;
        }

        double scale = Point2D.distance(x1, y1, x2, y2);

        gc.save();

        gc.setFill(color);

        gc.translate(x1, y1);
        gc.rotate(arrow_angle);
        gc.scale(scale, scale);

        gc.moveTo(0, 0);
        gc.lineTo(1, 0);

        gc.lineTo(fin_x, -fin_dy);
        gc.moveTo(    1,       0);
        gc.lineTo(fin_x,  fin_dy);

        gc.restore();
    }
}
