package lib.render;

import javafx.scene.canvas.Canvas;

import lib.debug.MethodNameHack;
import lib.javafx_api_extensions.javafx_support.Enums.*;

import static lib.debug.AssertMessages.BAD_CODE_PATH;
import static lib.debug.Debug.assert_msg;

public class View {

    public double dx;
    public double dy;
    public double scale;

    public View() {
        dx = 0;
        dy = 0;
        scale = 1;
    }

    public View(double dx, double dy, double scale) {
        this.dx = dx;
        this.dy = dy;
        this.scale = scale;
    }

    public WidthHeightToken scale(double scale_rel, ScaleOp scale_op) {
        Canvas canvas = gc.getCanvas();
        double scale;

        switch (scale_op) {
            case WIDTH:
                scale = canvas.getWidth() * scale_rel;
                gc.scale(scale, scale);
                update_min_radius(this::invert_x);
                return WidthHeightToken.WIDTH;
            case HEIGHT:
                scale = canvas.getHeight() * scale_rel;
                gc.scale(scale, scale);
                update_min_radius(this::invert_y);
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
