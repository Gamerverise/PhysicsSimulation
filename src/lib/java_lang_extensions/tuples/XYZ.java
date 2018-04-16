package lib.java_lang_extensions.tuples;

public class XYZ<T> {
    public T x;
    public T y;
    public T z;

    public XYZ(T x, T y, T z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static <T> XYZ<T> XY(T x, T y, T z) {
        return new XYZ<>(x, y, z);
    }
}
