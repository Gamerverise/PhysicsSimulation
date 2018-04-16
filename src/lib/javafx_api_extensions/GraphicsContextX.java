package lib.javafx_api_extensions;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.transform.NonInvertibleTransformException;
import lib.debug.MethodNameHack;
import lib.java_lang_extensions.tuples.XY;

import static lib.debug.AssertMessages.BAD_CODE_PATH;
import static lib.debug.Debug.assert_msg;
import static lib.java_lang_extensions.tuples.XY.XY;

public class GraphicsContextX {

    public GraphicsContext gc;

    public GraphicsContextX(GraphicsContext gc) {
        this.gc = gc;
    }

    public void fill_circle(double x, double y, double radius) {
        // FIXME: HIGH PRIORITY: Not quite centered when radius scaled to canvas width
        gc.fillOval(x - radius, y - radius, 2 * radius, 2 * radius);
    }

    public XY<Double> invert_coordinates(double x, double y) throws NonInvertibleTransformException {

        // FIXME: Fix this comment
        // Currently, invert_coordinates is only used in computing the min_radius.
        //
        // We only expect the NonInvertibleTransformException when the viewport is set to its default size of zero width and
        // zero height. As of now, if this exception was raised in any other circumstance, it would be a bug.

        Point2D xy = gc.getTransform().inverseTransform(0, y);
        return XY(xy.getX(), xy.getY());
    }

    public static GraphicsContext JAVA_HACK_get_graphics_context(Object canvas) {
        if (canvas instanceof Canvas)
            return ((Canvas) canvas).getGraphicsContext2D();

        assert false : assert_msg(
                new MethodNameHack() {
                }.method_name().getClass(),
                new MethodNameHack() {
                }.method_name(),
                BAD_CODE_PATH);

        return null;
    }
}