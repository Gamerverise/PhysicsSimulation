package lib.java_lang_extensions.tuples;

public class Range<T> {
    public T start;
    public T end;

    public Range(T start, T end) {
        this.start = start;
        this.end = end;
    }

    public static <T> Range<T> Range(T start, T end) {
        return new Range<>(start, end);
    }
}
