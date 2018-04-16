package lib.render.context;

import lib.render.Viewport;
import lib.render.device.CanvasGraphicsDevice;

public class CanvasRenderingGraphicsContext extends RenderingGraphicsContext<CanvasGraphicsDevice> {

    public CanvasRenderingGraphicsContext(CanvasGraphicsDevice device, Viewport viewport) {
        super(device, viewport);
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
}
