package lib.java_lang_extensions.anonymous_types;

public class TypedTuple<V> extends Tuple {

    @SuppressWarnings("unchecked")
    TypedTuple(int n) {
        data = (V[]) new Object[n];
    }

    @SafeVarargs
    TypedTuple(Object... values) {
        data = (V[]) values;
    }

    @SuppressWarnings("unchecked")
    V get(int i) {
        return (V) data[i];
    }

    void put(int i, V elem) {
        data[i] = elem;
    }

    @SafeVarargs
    @SuppressWarnings("unchecked")
    public static <T> TypedTuple T(T... values) {
        return new TypedTuple(values);
    }
}
