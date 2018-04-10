package lib.java_lang_extensions.anonymous_types;

public class TypedTuple<V> {
    V[] data;

    @SuppressWarnings("unchecked")
    TypedTuple(int n) {
        data = (V[]) new Object[n];
    }

    @SafeVarargs
    TypedTuple(V... values) {
        data = values;
    }

    <T> V get(int i) {
        return data[i];
    }

    void put(int i, V elem) {
        data[i] = elem;
    }

    @SafeVarargs
    @SuppressWarnings("unchecked")
    public static <T> TypedTuple<T> T(T... values) {
        return new TypedTuple<>(values);
    }
}
