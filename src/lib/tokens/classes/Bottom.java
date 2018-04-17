package lib.tokens.classes;

public class Bottom<T> {

    public T bottom;

    public Bottom(T bottom) {
        this.bottom = bottom;
    }

    public static <T> Bottom<T> Bottom(T top) {
        return new Bottom<>(top);
    }
}
