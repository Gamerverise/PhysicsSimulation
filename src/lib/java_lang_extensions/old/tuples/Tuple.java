package lib.java_lang_extensions.old.tuples;

import lib.java_lang_extensions.structs.*;
import lib.tokens.classes.*;

public class Tuple {

    ///// Pairs ////////////////////////////////////////////////////////////////////////////////

    public static <T> XY<T> T(X<T> x, Y<T> y) {
        return new XY<>(x.x, y.y);
    }

    public static <T> WidthHeight<T> T(Width<T> w, Height<T> h) {
        return new WidthHeight<>(w.width, h.height);
    }

    ///// Triples ////////////////////////////////////////////////////////////////////////////////

    public static <T> XYZ<T> T(X<T> x, Y<T> y, Z<T> z) {
        return new XYZ<>(x.x, y.y, z.z);
    }

    ///// Quadruples ////////////////////////////////////////////////////////////////////////////////

    public static <T> Geometry_XY_WH<T> T(X<T> x, Y<T> y, Width<T> width, Height<T> height) {
        return new Geometry_XY_WH<>(x.x, y.y, width.width, height.height);
    }

    public static <T> Geometry_TL_BR<T> T(Top<T> top, Left<T> left, Bottom<T> bottom, Right<T> right) {
        return new Geometry_TL_BR<>(top.top, left.left, bottom.bottom, right.right);
    }
}
