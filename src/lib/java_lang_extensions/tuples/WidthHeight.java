package lib.java_lang_extensions.tuples;

public class WidthHeight<T> {
    public T width;
    public T height;

    public WidthHeight(T width, T height) {
        this.width = width;
        this.height = height;
    }
    public static <T> WidthHeight<T> WidthHeight(T width, T height) {
        return new WidthHeight<>(width, height);
    }
}
