package lib.java_lang_extensions.anonymous_types;

public class Tuple {

    Object[] data;

    @SuppressWarnings("unchecked")
    Tuple(int n) {
        data = new Object[n];
    }

    @SafeVarargs
    Tuple(Object... values) {
        data = values;
    }

    <T> T get(int i) {
        return (T) data[i];
    }

    void put(int i, Object elem) {
        data[i] = elem;
    }

    @SafeVarargs
    @SuppressWarnings("unchecked")
    public static <T> Tuple T(T... values) {
        return new Tuple(values);
    }
}
