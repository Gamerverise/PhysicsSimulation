package lib.java_lang_extensions.anonymous_types;

public class TypedTuple<V> {
    V[] data;

    TypedTuple(int n) {
        data = (V[]) new Object[n];
    }

    TypedTuple(V... values) {
        data = values;
    }

    <T> V get(int i) {
        return data[i];
    }

    void put(int i, V elem) {
        data[i] = elem;
    }

    public static <T> TypedTuple T(T... values) {
        return new TypedTuple(values);
    }
}
