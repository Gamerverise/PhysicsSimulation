package lib.render.graphics_context;

import javafx.scene.transform.NonInvertibleTransformException;
import lib.graphics_device.VirtualGraphicsDevice;
import lib.java_lang_extensions.tuples.WidthHeight;
import lib.java_lang_extensions.tuples.XY;
import lib.javafx_api_extensions.AffineX;
import lib.render.Viewport;

import static lib.render.graphics_context.RenderingGraphicsContext.AspectRatioOption.SCALE_VIEWPORT_TO_FIT_DEVICE;

public abstract class RenderingGraphicsContext<DEVICE extends VirtualGraphicsDevice>
        extends PrimitiveGraphicsContext<DEVICE>
{
    public enum AspectRatioOption {
        KEEP_CURRENT_VIEWPORT,
        FIXED_GEOMETRY,
        SCALED_GEOMETRY,
        SCALE_VIEWPORT_TO_DEVICE_WIDTH,
        SCALE_VIEWPORT_TO_DEVICE_HEIGHT,
        SCALE_VIEWPORT_TO_FIT_DEVICE
    }

    public Viewport viewport;

    public AspectRatioOption aspect_ratio_option;

    public double viewport_center_x_device;
    public double viewport_center_y_device;

    public double viewport_center_x_device_rel = 0.5;
    public double viewport_center_y_device_rel = 0.5;
    public double width_scale = 1;
    public double height_scale = 1;

    public double min_radius_px = 2;
    public double min_radius_model = 2;

    RenderingGraphicsContext(DEVICE device) {
        super(device);
        this.viewport = new Viewport();
        aspect_ratio_option = SCALE_VIEWPORT_TO_FIT_DEVICE;
    }

    // begin_render should save the transform state, and apply the viewport and device transforms so that
    // the drawing of models appears correctly on the device

    public void begin_render() {
        push_transform();

        // Note: JavaFx's transform ordering for a GraphicsContext is FILO, where the first transform applied
        // to the GraphicsContext is the last one applied to the points of a graphics primitive.

        // 3) Map nominal device coordinates to actual device coordinates

        translate(device.device_transform_x_px, device.device_transform_y_px);

        scale(device.get_scale_x(), device.get_scale_y());

        // 2) Map viewport coordinates to nominal device coordinates

        double scale;

        switch (aspect_ratio_option) {
            case KEEP_CURRENT_VIEWPORT:
                break;

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

        XY<Double> viewport_center_model = viewport.get_center_model();

        // 1) Map model coordinates to viewport coordinates

        translate(viewport_center_model.x, viewport_center_model.y);

        update_min_radius();
    }

    public void end_render() {
        pop_transform();
    }

    public abstract void translate(double delta_x_context, double delta_y_context);

    public abstract void scale(double width_scale_context, double height_scale_context);

    public abstract void push_transform();

    public abstract void pop_transform();

    public void update_min_radius() {

        // To avoid distortion from scaling, we require that the transform from model coordinates to device
        // coordinates map a circle to a circle. As such, we may use either the x- or y-axis to determine the
        // minimum radius allowed. Currently, we use the x-axis.

        AffineX transform = get_transform();

        try {
            double inverted_radius = transform.inverseTransform(min_radius_px, 0).getX();
            double inverted_origin = transform.inverseTransform(0, 0).getX();
            min_radius_model = Math.abs(inverted_radius - inverted_origin);
        } catch (NonInvertibleTransformException e) {
            min_radius_model = 0;
        }
    }

    public abstract AffineX get_transform();

    public void fill_circle(double x, double y, double radius) {
        if (radius < min_radius_model)
            radius = min_radius_model;

        primitive_fill_circle(x, y, radius);
    }

    public abstract void primitive_fill_circle(double x, double y, double radius);
}
