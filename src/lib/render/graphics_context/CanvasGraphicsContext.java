package lib.render.graphics_context;

import lib.graphics_device.CanvasGraphicsDevice;

public class CanvasGraphicsContext extends PrimitiveGraphicsContext<CanvasGraphicsDevice> {

    public CanvasGraphicsContext() {
        super(new CanvasGraphicsDevice());
    }
}
