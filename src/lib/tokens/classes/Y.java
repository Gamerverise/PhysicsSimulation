package lib.tokens.classes;

public class Y<T> {

    public T y;

    public Y(T y) {
        this.y = y;
    }

    public static <T> Y<T> Y(T top) {
        return new Y(top);
    }
}
