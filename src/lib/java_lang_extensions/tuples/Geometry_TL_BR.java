package lib.java_lang_extensions.tuples;

public class Geometry_TL_BR<T> {

    public T top;
    public T left;
    public T bottom;
    public T right;

    public Geometry_TL_BR(T top, T left, T bottom, T right) {
        this.top = top;
        this.left = left;
        this.bottom = bottom;
        this.right = right;
    }

    public static <T> Geometry_TL_BR<T> Geometry_TL_BR(T top, T left, T bottom, T right) {
        return new Geometry_TL_BR<>(top, left, bottom, right);
    }
}
