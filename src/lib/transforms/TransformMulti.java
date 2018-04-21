package lib.transforms;

import lib.java_lang_extensions.tuples.XY;

public interface TransformMulti {

    void translate(double dx, double dy);

    void scale(double scale_x, double scale_y);

    void rotate(double degrees);

    XY<Double> map_point(double x, double y);

    // FIXME: Do we still need this scale function?

//    static WidthHeightToken scale(TransformMulti transform, double scale_rel, double width, double height, ScaleOp scale_op) {
//        double scale;
//
//        switch (scale_op) {
//            case WIDTH:
//                scale = width * scale_rel;
//                transform.scale(scale, scale);
//                return WidthHeightToken.WIDTH;
//            case HEIGHT:
//                scale = height * scale_rel;
//                transform.scale(scale, scale);
//                return WidthHeightToken.HEIGHT;
//
//            case HALF_WIDTH:
//                return scale(transform, scale_rel/2, width, height, ScaleOp.WIDTH);
//            case HALF_HEIGHT:
//                return scale(transform, scale_rel/2, width, height, ScaleOp.HEIGHT);
//
//            case MAX:
//                if (width > height)
//                    return scale(transform, scale_rel, width, height, ScaleOp.WIDTH);
//                else
//                    return scale(transform, scale_rel, width, height, ScaleOp.HEIGHT);
//            case MIN:
//                if (width < height)
//                    return scale(transform, scale_rel, width, height, ScaleOp.WIDTH);
//                else
//                    return scale(transform, scale_rel, width, height, ScaleOp.HEIGHT);
//
//            case HALF_MAX:
//                if (width > height)
//                    return scale(transform, scale_rel/2, width, height, ScaleOp.WIDTH);
//                else
//                    return scale(transform, scale_rel/2, width, height, ScaleOp.HEIGHT);
//            case HALF_MIN:
//                if (width < height)
//                    return scale(transform, scale_rel/2, width, height, ScaleOp.WIDTH);
//                else
//                    return scale(transform, scale_rel/2, width, height, ScaleOp.HEIGHT);
//        }
//
//        MethodNameHack hack = new MethodNameHack() {};
//
//        assert false : assert_msg(
//                hack.method_name().getClass(),
//                hack.method_name(),
//                BAD_CODE_PATH);
//
//        return null;
//    }
}
