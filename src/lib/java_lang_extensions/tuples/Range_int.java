package lib.java_lang_extensions.tuples;

public class Range_int<T> {
    public T start;
    public T end;

    public Range_int(T start, T end) {
        this.start = start;
        this.end = end;
    }

    public static <T> Range_int<T> Range(T start, T end) {
        return new Range_int<>(start, end);
    }
}
