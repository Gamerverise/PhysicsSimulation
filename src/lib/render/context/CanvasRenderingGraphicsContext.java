package lib.render.context;

import javafx.scene.canvas.Canvas;
import javafx.scene.transform.Affine;
import lib.debug.MethodNameHack;
import lib.javafx_api_extensions.javafx_support.ScaleOp;
import lib.render.Viewport;
import lib.render.device.CanvasGraphicsDevice;
import lib.tokens.enums.WidthHeightToken;

import static lib.debug.AssertMessages.BAD_CODE_PATH;
import static lib.debug.Debug.assert_msg;

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

    public Affine get_transform() {
        return device.gcx.gc.getTransform();
    }

    public WidthHeightToken scale(double scale_rel, ScaleOp scale_op) {
        Canvas canvas = device.gcx.gc.getCanvas();
        double scale;

        switch (scale_op) {
            case WIDTH:
                scale = canvas.getWidth() * scale_rel;
                device.gcx.gc.scale(scale, scale);
                return WidthHeightToken.WIDTH;
            case HEIGHT:
                scale = canvas.getHeight() * scale_rel;
                device.gcx.gc.scale(scale, scale);
                return WidthHeightToken.HEIGHT;

            case HALF_WIDTH:
                return scale(scale_rel/2, ScaleOp.WIDTH);
            case HALF_HEIGHT:
                return scale(scale_rel/2, ScaleOp.HEIGHT);

            case MAX:
                if (canvas.getWidth() > canvas.getHeight())
                    return scale(scale_rel, ScaleOp.WIDTH);
                else
                    return scale(scale_rel, ScaleOp.HEIGHT);
            case MIN:
                if (canvas.getWidth() < canvas.getHeight())
                    return scale(scale_rel, ScaleOp.WIDTH);
                else
                    return scale(scale_rel, ScaleOp.HEIGHT);

            case HALF_MAX:
                if (canvas.getWidth() > canvas.getHeight())
                    return scale(scale_rel/2, ScaleOp.WIDTH);
                else
                    return scale(scale_rel/2, ScaleOp.HEIGHT);
            case HALF_MIN:
                if (canvas.getWidth() < canvas.getHeight())
                    return scale(scale_rel/2, ScaleOp.WIDTH);
                else
                    return scale(scale_rel/2, ScaleOp.HEIGHT);
        }

        assert false : assert_msg(
                this.getClass(),
                new MethodNameHack(){}.method_name(),
                BAD_CODE_PATH);

        return null;
    }
}
