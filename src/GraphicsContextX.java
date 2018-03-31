import javafx.scene.canvas.GraphicsContext;
import javafx.scene.transform.NonInvertibleTransformException;

public class GraphicsContextX {
    GraphicsContext gc;

    double min_radius;

    GraphicsContextX(GraphicsContext gc) {
        this.gc = gc;
    }

    void fill_circle(double x, double y, double radius) {
        if (radius < min_radius)
            radius = min_radius;

        // FIXME: Not quite centered when radius scaled to canvas width
        gc.fillOval(x - radius, y - radius, 2*radius, 2*radius);
    }

    public void scale(double x_scale, double y_scale, double px_dimension) {
        gc.scale(x_scale, y_scale);
    }
}
