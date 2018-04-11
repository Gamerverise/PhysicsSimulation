package lib.javafx_api_extensions;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.transform.NonInvertibleTransformException;
import javafx.scene.transform.Transform;
import lib.debug.MethodNameHack;
import lib.java_lang_extensions.function_types.FunctionR1_NonInvertibleExc;

import static lib.debug.AssertMessages.BAD_CODE_PATH;
import static lib.debug.Debug.assert_msg;
import static lib.javafx_api_extensions.javafx_support.Enums.Dimension2D;
import static lib.javafx_api_extensions.javafx_support.Enums.ScaleOp2D;

public class GraphicsContextX {
    public GraphicsContext gc;

    // Coordinates get transformed as follows:
    //
    //     model -> normalized view -> actual view (in pixels)
    //
    // The first step, "model -> normalized view", is effected by these:
    //
    //     * gc.translate
    //     * gcx.scale        (where gcx stands for this GraphicsContextX)
    //     * gc.rotate
    //
    // The viewport_transform does the last step:
    //
    //     normalized view -> actual view (in pixels)

    public Transform viewport_transform;

    public double min_radius_px;
    public double min_radius;

    public GraphicsContextX(GraphicsContext gc, double min_radius_px) {
        this.gc = gc;
        this.min_radius_px = min_radius_px;

        // Default viewport has zero width and zero height

        gc.scale(0, 0);
        min_radius = 0;
        gc.save();
    }

    public void fill_circle(double x, double y, double radius) {
        if (radius < min_radius)
            radius = min_radius;

        // FIXME: HIGH PRIORITY: Not quite centered when radius scaled to canvas width
        gc.fillOval(x - radius, y - radius, 2*radius, 2*radius);
    }

    public double invert_x(double x) throws NonInvertibleTransformException {

        // Currently, invert_x is only used in computing the min_radius.
        //
        // We only expect the NonInvertibleTransformException when the viewport is set to its default size of zero width and
        // zero height. As of now, if this exception was raised in any other circumstance, it would be a bug.

        return gc.getTransform().inverseTransform(x, 0).getX();
    }

    public double invert_y(double y) throws NonInvertibleTransformException {
        return gc.getTransform().inverseTransform(0, y).getY();

        // Currently, invert_y is only here for completeness. We are not using it at all. See the comment for
        // invert_x for a full explanation.
    }

    public void update_min_radius(FunctionR1_NonInvertibleExc<Double, Double> inverter) {

        // To avoid distortion from scaling, we require that the rectangle of the model mapped into the viewport has
        // same aspect ratio as the viewport. (In other words, a circle will remain a circle.) As such, we may use
        // either the x- or y-axis to determine the minimum radius allowed. Currently, we use the x-axis.

        try {
            double inverted_radius = inverter.call(min_radius_px);
            double inverted_origin = inverter.call(0d);
            min_radius = Math.abs(inverted_radius - inverted_origin);
        } catch (NonInvertibleTransformException e) {
            min_radius = 0;
        }
    }

    public Dimension2D scale(double scale_rel, ScaleOp2D scale_op) {
        Canvas canvas = gc.getCanvas();
        double scale;

        switch (scale_op) {
            case WIDTH:
                scale = canvas.getWidth() * scale_rel;
                gc.scale(scale, scale);
                update_min_radius(this::invert_x);
                return Dimension2D.WIDTH;
            case HEIGHT:
                scale = canvas.getHeight() * scale_rel;
                gc.scale(scale, scale);
                update_min_radius(this::invert_y);
                return Dimension2D.HEIGHT;

            case HALF_WIDTH:
                return scale(scale_rel/2, ScaleOp2D.WIDTH);
            case HALF_HEIGHT:
                return scale(scale_rel/2, ScaleOp2D.HEIGHT);

            case MAX:
                if (canvas.getWidth() > canvas.getHeight())
                    return scale(scale_rel, ScaleOp2D.WIDTH);
                else
                    return scale(scale_rel, ScaleOp2D.HEIGHT);
            case MIN:
                if (canvas.getWidth() < canvas.getHeight())
                    return scale(scale_rel, ScaleOp2D.WIDTH);
                else
                    return scale(scale_rel, ScaleOp2D.HEIGHT);

            case HALF_MAX:
                if (canvas.getWidth() > canvas.getHeight())
                    return scale(scale_rel/2, ScaleOp2D.WIDTH);
                else
                    return scale(scale_rel/2, ScaleOp2D.HEIGHT);
            case HALF_MIN:
                if (canvas.getWidth() < canvas.getHeight())
                    return scale(scale_rel/2, ScaleOp2D.WIDTH);
                else
                    return scale(scale_rel/2, ScaleOp2D.HEIGHT);
        }

        assert false : assert_msg(
                this.getClass(),
                new MethodNameHack(){}.method_name(),
                BAD_CODE_PATH);
        return null;
    }

    public void set_viewport(double width, double height) {
        gc.restore();
        gc.save();

        gc.scale(width, height);
        update_min_radius(this::invert_x);
    }
}
