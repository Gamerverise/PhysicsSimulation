package lib.transforms;

import lib.debug.MethodNameHack;
import lib.javafx_api_extensions.javafx_api_extensions_support.ScaleOp;
import lib.tokens.enums.WidthHeightToken;

import static lib.debug.AssertMessages.BAD_CODE_PATH;
import static lib.debug.Debug.assert_msg;

public interface TransformMulti {

    void translate(double dx, double dy);

    void scale(double scale_x, double scale_y);

    void rotate(double degrees);

    static WidthHeightToken scale(TransformMulti transform, double scale_rel, double width, double height, ScaleOp scale_op) {
        double scale;

        switch (scale_op) {
            case WIDTH:
                scale = width * scale_rel;
                transform.scale(scale, scale);
                return WidthHeightToken.WIDTH;
            case HEIGHT:
                scale = height * scale_rel;
                transform.scale(scale, scale);
                return WidthHeightToken.HEIGHT;

            case HALF_WIDTH:
                return scale(transform, scale_rel/2, width, height, ScaleOp.WIDTH);
            case HALF_HEIGHT:
                return scale(transform, scale_rel/2, width, height, ScaleOp.HEIGHT);

            case MAX:
                if (width > height)
                    return scale(transform, scale_rel, width, height, ScaleOp.WIDTH);
                else
                    return scale(transform, scale_rel, width, height, ScaleOp.HEIGHT);
            case MIN:
                if (width < height)
                    return scale(transform, scale_rel, width, height, ScaleOp.WIDTH);
                else
                    return scale(transform, scale_rel, width, height, ScaleOp.HEIGHT);

            case HALF_MAX:
                if (width > height)
                    return scale(transform, scale_rel/2, width, height, ScaleOp.WIDTH);
                else
                    return scale(transform, scale_rel/2, width, height, ScaleOp.HEIGHT);
            case HALF_MIN:
                if (width < height)
                    return scale(transform, scale_rel/2, width, height, ScaleOp.WIDTH);
                else
                    return scale(transform, scale_rel/2, width, height, ScaleOp.HEIGHT);
        }

        assert false : assert_msg(
                new MethodNameHack(){}.method_name().getClass(),
                new MethodNameHack(){}.method_name(),
                BAD_CODE_PATH);

        return null;
    }
}
