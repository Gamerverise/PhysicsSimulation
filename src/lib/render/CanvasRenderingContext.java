package lib.render;

import javafx.scene.canvas.GraphicsContext;

import static lib.render.CanvasRenderingDevice.*;

public class CanvasRenderingContext extends RenderingGraphicsContext<CanvasRenderingDevice> {

    CanvasRenderingContext(Viewport viewport, CanvasRenderingDevice device) {
        super(viewport, device);
    }

    public void begin_render() {

        GraphicsContext gc = device.gcx.gc;
        gc.save();

        gc.translate(viewport.viewport_center_x_model, viewport.viewport_center_y_model);
        gc.scale(device.getWidth() / viewport.viewport_width_model,
                 device.getHeight() / viewport.viewport_center_y_model);

        gc.translate(device.device_transform_x_px, device.device_transform_y_px);
        gc.scale(device_transform_scale_x, device_transform_scale_y);
    }

    public void end_render() {
        device.gcx.gc.restore();
    }
}
