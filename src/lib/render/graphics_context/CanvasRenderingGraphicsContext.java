package lib.render.graphics_context;

import lib.graphics_device.CanvasGraphicsDevice;
import lib.javafx_api_extensions.AffineX;

public class CanvasRenderingGraphicsContext extends RenderingGraphicsContext<CanvasGraphicsDevice> {

    public CanvasRenderingGraphicsContext(CanvasGraphicsDevice device) {
        super(device);
    }

    public void translate(double delta_x_context, double delta_y_context) {
        device.gcx.gc.translate(delta_x_context, delta_y_context);
    }

    public void scale(double width_scale_context, double height_scale_context) {
        device.gcx.gc.scale(width_scale_context, height_scale_context);
    }

    public void push_transform() {
        device.gcx.gc.save();
    }

    public void pop_transform() {
        device.gcx.gc.restore();
    }

    public AffineX get_transform() {
        return new AffineX(device.gcx.gc.getTransform());
    }

    public void primitive_fill_circle(double x, double y, double radius) {
        device.gcx.fill_circle(x, y, radius);
    }
}
