package lib.tokens.classes;

public class Left<T> {

    public T left;

    public Left(T left) {
        this.left = left;
    }

    public static <T> Left<T> Left(T top) {
        return new Left(top);
    }
}
