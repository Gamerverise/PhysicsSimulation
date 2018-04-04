package lib.java_lang_extensions.anonymous_types;

public class Tuple<T> {
    T[] data;

    Tuple(int n) {
        data = (T[]) new Object[n];
    }

    Tuple(T... values) {
        data = values;
    }

    <K extends T> K get(int i) {
        return (K) data[i];
    }

    void put(int i, T elem) {
        data[i] = elem;
    }

    public static <T> Tuple T(T... values) {
        return new Tuple(values);
    }
}
