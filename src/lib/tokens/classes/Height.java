package lib.tokens.classes;

public class Height<T> {

    public T height;

    public Height(T height) {
        this.height = height;
    }

    public static <T> Height<T> Height(T top) {
        return new Height(top);
    }
}
