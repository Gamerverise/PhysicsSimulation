package lib.javafx_api_extensions;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.transform.NonInvertibleTransformException;

import lib.debug.MethodNameHack;
import lib.java_lang_extensions.function_types.FunctionR1;

import static lib.debug.AssertMessages.BAD_CODE_PATH;
import static lib.debug.Debug.assert_msg;
import static lib.javafx_api_extensions.javafx_support.Enums.ScaleOp2D;
import static lib.javafx_api_extensions.javafx_support.Enums.Dimension2D;

public class GraphicsContextX {
    public GraphicsContext gc;

    public double min_radius_px;
    public double min_radius;

    public GraphicsContextX(GraphicsContext gc, double min_radius_px) {
        this.gc = gc;
        this.min_radius_px = min_radius_px;
        min_radius = min_radius_px;
    }

    public void fill_circle(double x, double y, double radius) {
        if (radius < min_radius)
            radius = min_radius;

        // FIXME: Not quite centered when radius scaled to canvas width
        gc.fillOval(x - radius, y - radius, 2*radius, 2*radius);
    }

    public double invert_x(double x) {
        try {
            return gc.getTransform().inverseTransform(x, 0).getX();
        } catch (NonInvertibleTransformException e) {
            assert false : assert_msg(
                    this.getClass(),
                    new MethodNameHack() {
                    }.method_name(),
                    BAD_CODE_PATH);
        }

        return Double.NaN;
    }

    public double invert_y(double y) {
        try {
            return gc.getTransform().inverseTransform(0, y).getY();
        } catch (NonInvertibleTransformException e) {
            assert false : assert_msg(
                    this.getClass(),
                    new MethodNameHack() {
                    }.method_name(),
                    BAD_CODE_PATH);
        }

        return Double.NaN;
    }

    public void update_min_radius(FunctionR1<Double, Double> inverter) {
        double inverted_radius = inverter.call(min_radius_px);
        double inverted_origin = inverter.call(0d);
        min_radius = Math.abs(inverted_radius - inverted_origin);
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
}
