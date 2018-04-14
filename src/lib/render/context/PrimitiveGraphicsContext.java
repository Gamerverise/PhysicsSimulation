package lib.render.context;

import lib.render.device.GraphicsDevice;

public abstract class PrimitiveGraphicsContext<DEVICE extends GraphicsDevice> {
    DEVICE device;

    // Drawing primitives for a device should be organized into a sub-class of PrimitiveGraphicsContext

    public PrimitiveGraphicsContext(DEVICE device) {
        this.device = device;
    }
}
