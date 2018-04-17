package lib.tokens.classes;

public class X<T> {

    public T x;

    public X(T x) {
        this.x = x;
    }

    public static <T> X<T> X(T top) {
        return new X<>(top);
    }
}
