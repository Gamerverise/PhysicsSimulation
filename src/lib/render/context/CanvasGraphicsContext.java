package lib.render.context;

import lib.render.device.CanvasGraphicsDevice;

public class CanvasGraphicsContext extends PrimitiveGraphicsContext<CanvasGraphicsDevice> {

    public CanvasGraphicsContext() {
        super(new CanvasGraphicsDevice());
    }
}
