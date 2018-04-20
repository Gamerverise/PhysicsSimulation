package lib.render.graphics_context;

import lib.graphics_device.CanvasGraphicsDevice;
import lib.java_lang_extensions.tuples.XY;
import lib.javafx_api_extensions.AffineX;

import static lib.java_lang_extensions.tuples.XY.XY;

public class CanvasRenderingGraphicsContext extends RenderingGraphicsContext<CanvasGraphicsDevice> {

    public CanvasRenderingGraphicsContext(CanvasGraphicsDevice device) {
        super(device);
    }

    public void translate(double delta_x_context, double delta_y_context) {
        device.cgc.gc.translate(delta_x_context, delta_y_context);
    }

    public void scale(double width_scale_context, double height_scale_context) {
        device.cgc.gc.scale(width_scale_context, height_scale_context);
    }

    public void rotate(double degrees) {
        device.cgc.gc.rotate(degrees);
    }

    public XY<Double> map_point(double x, double y) {
        return XY(device.cgc.gc.getTransform().transform(x, y));
    }

    public void push_transform() {
        device.cgc.gc.save();
    }

    public void pop_transform() {
        device.cgc.gc.restore();
    }

    public AffineX get_transform() {
        return new AffineX(device.cgc.gc.getTransform());
    }

    public void primitive_fill_circle(double x, double y, double radius) {
        device.cgc.fill_circle(x, y, radius);
    }
}
