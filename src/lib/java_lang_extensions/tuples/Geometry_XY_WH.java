package lib.java_lang_extensions.tuples;

public class Geometry_XY_WH<T> {
    public T x;
    public T y;
    public T width;
    public T height;

    public Geometry_XY_WH(T x, T y, T w, T h) {
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
    }
    public static <T> Geometry_XY_WH<T> Geometry_XY_WH(T x, T y, T width, T height) {
        return new Geometry_XY_WH<>(x, y, width, height);
    }
}
