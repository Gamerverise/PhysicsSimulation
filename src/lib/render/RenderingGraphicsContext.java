package lib.render;

import javafx.scene.canvas.GraphicsContext;
import lib.javafx_api_extensions.GraphicsContextX;

public abstract class RenderingGraphicsContext<RENDERING_DEVICE extends VirtualRenderingDevice> extends GraphicsContextX {

    Viewport viewport;
    RENDERING_DEVICE device;

    RenderingGraphicsContext(Viewport viewport, RENDERING_DEVICE device) {
        super(GraphicsContextX.JAVA_HACK_get_graphics_context(device));

        this.viewport = viewport;
        this.device = device;
    }

    // begin_render should save the transform state, and apply the viewport and device transforms so that
    // the drawing of models appears correctly on the device

    public abstract void begin_render();

    // end_render should restore the transform state so that it is ready for the next call to begin_render

    public abstract void end_render();
}
