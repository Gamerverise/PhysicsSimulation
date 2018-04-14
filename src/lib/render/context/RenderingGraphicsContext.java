package lib.render.context;

import lib.javafx_api_extensions.GraphicsContextX;
import lib.render.Viewport;
import lib.render.device.GraphicsDevice;

public abstract class RenderingGraphicsContext<DEVICE extends GraphicsDevice>
        extends PrimitiveGraphicsContext<DEVICE>
{

    Viewport viewport;

    RenderingGraphicsContext(DEVICE device, Viewport viewport) {
        super(device);
        this.viewport = viewport;
    }

    // begin_render should save the transform state, and apply the viewport and device transforms so that
    // the drawing of models appears correctly on the device

    public abstract void begin_render();

    // end_render should restore the transform state so that it is ready for the next call to begin_render

    public abstract void end_render();
}
