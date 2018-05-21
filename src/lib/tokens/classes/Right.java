package lib.tokens.classes;

public class Right<T> {

    public T right;

    public Right(T right) {
        this.right = right;
    }

    public static <T> Right<T> Right(T right) {
        return new Right<>(right);
    }
}
