package lib.render.context;

import lib.render.Viewport;
import lib.render.device.GraphicsDevice;

import static lib.render.context.RenderingGraphicsContext.AspectRatioOption.SCALE_TO_FIT;

public abstract class RenderingGraphicsContext<DEVICE extends GraphicsDevice>
        extends PrimitiveGraphicsContext<DEVICE>
{
    public enum AspectRatioOption {
        FIXED_GEOMETRY,
        FIXED_SCALE_GEOMETRY,
        SCALE_TO_FIT,           // SCALE_TO_FIT is the only option implemented so far
        MATCH_WIDTH,
        MATCH_HEIGHT
    }

    Viewport viewport;

    public AspectRatioOption aspect_ratio_option;

    RenderingGraphicsContext(DEVICE device, Viewport viewport) {
        super(device);
        this.viewport = viewport;
        aspect_ratio_option = SCALE_TO_FIT;
    }

    // begin_render should save the transform state, and apply the viewport and device transforms so that
    // the drawing of models appears correctly on the device

    public abstract void begin_render();

    // end_render should restore the transform state so that it is ready for the next call to begin_render

    public abstract void end_render();

    public void set_viewport_scale(double px_per_unit_model) {
        gcx.px_per_unit_model = px_per_unit_model;
    }
}
