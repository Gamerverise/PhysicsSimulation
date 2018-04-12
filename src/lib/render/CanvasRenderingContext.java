package lib.render;

import static lib.render.CanvasRenderingDevice.*;

public class CanvasRenderingContext implements RenderingContext {

    CanvasRenderingDevice device;
    Viewport viewport;

    public void begin_render() {
        device.gcx.gc.save();

        viewport.

        device.gcx.gc.translate(device.device_transform_x_px, device.device_transform_y_px);
        device.gcx.gc.scale(device_transform_scale_x, device_transform_scale_y);
    }

    public void end_render() {
        device.gcx.gc.restore();
    }
}
