package lib.tokens.classes;

public class Z<T> {

    public T z;

    public Z(T z) {
        this.z = z;
    }

    public static <T> Z<T> Z(T top) {
        return new Z<>(top);
    }
}
