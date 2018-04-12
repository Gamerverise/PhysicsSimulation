package lib.javafx_api_extensions;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.transform.NonInvertibleTransformException;
import lib.java_lang_extensions.anonymous_types.Pair;

import static lib.java_lang_extensions.anonymous_types.Pair.P;

public class GraphicsContextX {

    public GraphicsContext gc;

    public GraphicsContextX(GraphicsContext gc) {
        this.gc = gc;
    }

    public void fill_circle(double x, double y, double radius) {
        // FIXME: HIGH PRIORITY: Not quite centered when radius scaled to canvas width
        gc.fillOval(x - radius, y - radius, 2*radius, 2*radius);
    }

    public Pair<Double> invert_coordinates(double x, double y) throws NonInvertibleTransformException {

        // FIXME: Fix this comment
        // Currently, invert_coordinates is only used in computing the min_radius.
        //
        // We only expect the NonInvertibleTransformException when the viewport is set to its default size of zero width and
        // zero height. As of now, if this exception was raised in any other circumstance, it would be a bug.

        Point2D xy = gc.getTransform().inverseTransform(0, y);
        return P(xy.getX(), xy.getY());
    }
}
