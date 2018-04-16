package lib.tokens.classes;

public class Top<T> {

    public T top;

    public Top(T top) {
        this.top = top;
    }

    public static <T> Top<T> Top(T top) {
        return new Top(top);
    }
}
