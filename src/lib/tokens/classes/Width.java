package lib.tokens.classes;

public class Width<T> {

    public T width;

    public Width(T width) {
        this.width = width;
    }

    public static <T> Width<T> Width(T width) {
        return new Width<>(width);
    }
}
