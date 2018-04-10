package lib.java_lang_extensions.anonymous_types;

public class Tuple extends TypedTuple<Object> {

    @SuppressWarnings("unchecked")
    Tuple(int n) {
        data = new Object[n];
    }

    @SafeVarargs
    Tuple(Object... values) {
        data = values;
    }

    @SuppressWarnings("unchecked")
    <T> T get(int i) {
        return (T) data[i];
    }

    @SafeVarargs
    @SuppressWarnings("unchecked")
    public static Tuple T(Object... values) {
        return new Tuple(values);
    }
}
