package lib.java_lang_extensions.tuples;

public class Range_int {
    public int start;
    public int end;

    public Range_int(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public static Range_int Range_int(int start, int end) {
        return new Range_int(start, end);
    }

    public int size() {
        return end - start;
    }
}
