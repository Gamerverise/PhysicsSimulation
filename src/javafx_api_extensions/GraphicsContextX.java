package javafx_api_extensions;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.transform.NonInvertibleTransformException;
import lib_2.Enums;
import lib_2.RuntimeErrors;
import lib_2.java_lang_extensions.function_types.Sig_double_double;

import static lib_2.Enums.AxisName2D.X;
import static lib_2.Enums.AxisName2D.Y;

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
        } catch (NonInvertibleTransformException e) {}

        assert false : "GraphicsContextX.invert_x: " + RuntimeErrors.BAD_CODE_PATH;
        return Double.NaN;
    }

    public double invert_y(double y) {
        try {
            return gc.getTransform().inverseTransform(0, y).getY();
        } catch (NonInvertibleTransformException e) {}

        assert false : "GraphicsContextX.invert_y: " + RuntimeErrors.BAD_CODE_PATH;
        return Double.NaN;
    }

    public void update_min_radius(Sig_double_double inverter) {
        double inverted_radius = inverter.call(min_radius_px);
        double inverted_origin = inverter.call(0);
        min_radius = Math.abs(inverted_radius - inverted_origin);
    }

    public Enums.Dimension2D scale(double scale_rel, Enums.ScaleOp2D scale_op) {
        Canvas canvas = gc.getCanvas();
        double scale;

        switch (scale_op) {
            case WIDTH:
                scale = canvas.getWidth() * scale_rel;
                gc.scale(scale, scale);
                update_min_radius(this::invert_x);
                return Enums.Dimension2D.WIDTH;
            case HEIGHT:
                scale = canvas.getHeight() * scale_rel;
                gc.scale(scale, scale);
                update_min_radius(this::invert_y);
                return Enums.Dimension2D.HEIGHT;

            case HALF_WIDTH:
                return scale(scale_rel/2, Enums.ScaleOp2D.WIDTH);
            case HALF_HEIGHT:
                return scale(scale_rel/2, Enums.ScaleOp2D.HEIGHT);

            case MAX:
                if (canvas.getWidth() > canvas.getHeight())
                    return scale(scale_rel, Enums.ScaleOp2D.WIDTH);
                else
                    return scale(scale_rel, Enums.ScaleOp2D.HEIGHT);
            case MIN:
                if (canvas.getWidth() < canvas.getHeight())
                    return scale(scale_rel, Enums.ScaleOp2D.WIDTH);
                else
                    return scale(scale_rel, Enums.ScaleOp2D.HEIGHT);

            case HALF_MAX:
                if (canvas.getWidth() > canvas.getHeight())
                    return scale(scale_rel/2, Enums.ScaleOp2D.WIDTH);
                else
                    return scale(scale_rel/2, Enums.ScaleOp2D.HEIGHT);
            case HALF_MIN:
                if (canvas.getWidth() < canvas.getHeight())
                    return scale(scale_rel/2, Enums.ScaleOp2D.WIDTH);
                else
                    return scale(scale_rel/2, Enums.ScaleOp2D.HEIGHT);
        }

        assert false : "GraphicsContextX.scale: " + RuntimeErrors.BAD_CODE_PATH;
        return null;
    }
}
