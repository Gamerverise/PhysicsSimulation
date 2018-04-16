package lib.render.context;

import javafx.geometry.Point2D;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;
import lib.java_lang_extensions.function_types.FunctionR1_NonInvertibleExc;
import lib.java_lang_extensions.tuples.WidthHeight;
import lib.java_lang_extensions.tuples.XY;
import lib.render.Viewport;
import lib.render.device.VirtualGraphicsDevice;

import static lib.render.context.RenderingGraphicsContext.AspectRatioOption.SCALE_VIEWPORT_TO_FIT_DEVICE;
import static lib.java_lang_extensions.tuples.XY.XY;

public abstract class RenderingGraphicsContext<DEVICE extends VirtualGraphicsDevice>
        extends PrimitiveGraphicsContext<DEVICE>
{
    public enum AspectRatioOption {
        FIXED_GEOMETRY,
        SCALED_GEOMETRY,
        SCALE_VIEWPORT_TO_DEVICE_WIDTH,
        SCALE_VIEWPORT_TO_DEVICE_HEIGHT,
        SCALE_VIEWPORT_TO_FIT_DEVICE
    }

    Viewport viewport;

    public AspectRatioOption aspect_ratio_option;

    public double viewport_center_x_device;
    public double viewport_center_y_device;

    public double viewport_center_x_device_rel = 0.5;
    public double viewport_center_y_device_rel = 0.5;
    public double width_scale = 1;
    public double height_scale = 1;

    public double min_radius_px = 2;
    public double min_radius_model = 2;

    RenderingGraphicsContext(DEVICE device, Viewport viewport) {
        super(device);
        this.viewport = viewport;
        aspect_ratio_option = SCALE_VIEWPORT_TO_FIT_DEVICE;
    }

    // begin_render should save the transform state, and apply the viewport and device transforms so that
    // the drawing of models appears correctly on the device

    public void begin_render() {
        push_transform();

        // 1) Map model coordinates to viewport coordinates

        XY<Double> viewport_center_model = viewport.get_center_model();

        translate(viewport_center_model.x, viewport_center_model.y);

        // 2) Map viewport coordinates to nominal device coordinates

        double scale;

        switch (aspect_ratio_option) {
            case FIXED_GEOMETRY:
                translate(viewport_center_x_device, viewport_center_y_device);
                break;

            case SCALED_GEOMETRY:
                translate(
                        viewport_center_x_device_rel * device.get_width_px(),
                        viewport_center_y_device_rel * device.get_height_px()
                );

                scale(width_scale, height_scale);
                break;

            case SCALE_VIEWPORT_TO_DEVICE_WIDTH:
                scale = device.get_width_px() / viewport.get_width_model();
                scale(scale, scale);
                break;

            case SCALE_VIEWPORT_TO_DEVICE_HEIGHT:
                scale = device.get_height_px() / viewport.get_height_model();
                scale(scale, scale);
                break;

            case SCALE_VIEWPORT_TO_FIT_DEVICE:
                WidthHeight<Double> viewport_dims = viewport.get_dimensions_model();
                double viewport_aspect_ratio = viewport_dims.width / viewport_dims.height;

                if (viewport_aspect_ratio > device.get_aspect_ratio())
                    scale = device.get_width_px() / viewport_dims.width;
                else
                    scale = device.get_height_px() / viewport_dims.height;

                scale(scale, scale);
                break;
        }

        // 2) Map nominal device coordinates to actual device coordinates

        translate(device.device_transform_x_px, device.device_transform_y_px);

        scale(device.get_scale_x(), device.get_scale_y());

        update_min_radius(this.invert_x);
    }

    public void end_render() {
        pop_transform();
    }

    public abstract void translate(double delta_x_context, double delta_y_context);

    public abstract void scale(double width_scale_context, double height_scale_context);

    public abstract void push_transform();

    public abstract void pop_transform();

    public void update_min_radius(FunctionR1_NonInvertibleExc<Double, Double> inverter) {

        // To avoid distortion from scaling, we require that the transform from model coordinates to device
        // coordinates map a circle to a circle. As such, we may use either the x- or y-axis to determine the minimum
        // radius allowed. Currently, we use the x-axis.

        try {
            double inverted_radius = inverter.call(min_radius_px);
            double inverted_origin = inverter.call(0d);
            min_radius_model = Math.abs(inverted_radius - inverted_origin);
        } catch (NonInvertibleTransformException e) {
            min_radius_model = 0;
        }
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

    public Affine get_inverse_transform() {

    }
}
