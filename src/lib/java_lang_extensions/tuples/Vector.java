package lib.java_lang_extensions.tuples;

public class Vector<T> {
    public T x;
    public T y;

    public Vector(T x, T y) {
        this.x = x;
        this.y = y;
    }

    public static <T> Vector<T> Vector(T x, T y) {
        return new Vector<>(x, y);
    }
}
