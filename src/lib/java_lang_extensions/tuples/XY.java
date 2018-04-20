package lib.java_lang_extensions.tuples;

import javafx.geometry.Point2D;

public class XY<T> {
    public T x;
    public T y;

    public XY(T x, T y) {
        this.x = x;
        this.y = y;
    }

    public static <T> XY<T> XY(T x, T y) {
        return new XY<>(x, y);
    }

    public static XY<Double> XY(Point2D p) {
        return new XY<>(p.getX(), p.getY());
    }
}
