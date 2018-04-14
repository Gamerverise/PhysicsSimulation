package lib.render.context;

import javafx.scene.canvas.GraphicsContext;
import lib.render.device.CanvasGraphicsDevice;
import lib.render.Viewport;

import static lib.render.device.CanvasGraphicsDevice.*;

public class CanvasRenderingGraphicsContext extends RenderingGraphicsContext<CanvasGraphicsDevice> {

    public CanvasRenderingGraphicsContext(CanvasGraphicsDevice device, Viewport viewport) {
        super(device, viewport);
    }

    public void begin_render() {
        GraphicsContext gc = device.gcx.gc;
        gc.save();

        gc.translate(viewport.viewport_center_x_model, viewport.viewport_center_y_model);
        gc.scale(device.canvas.getWidth() / viewport.viewport_width_model,
                 device.canvas.getHeight() / viewport.viewport_center_y_model);

        gc.translate(device.device_transform_x_px, device.device_transform_y_px);
        gc.scale(device_transform_scale_x, device_transform_scale_y);
    }

    public void end_render() {
        device.gcx.gc.restore();
    }
}
