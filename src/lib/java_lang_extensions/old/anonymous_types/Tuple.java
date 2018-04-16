package lib.java_lang_extensions.old.anonymous_types;

public class Tuple<V> {

    // There is no point in trying to make one Tuple class for fixed-type cases, and one for variable-type
    // cases because Java does not enforce type checking of generic types. In particular, the type parameters
    // for a generic class are not required. Furthermore, limitations of Java make any such attempt an awful
    // hack. The reasons are, for a generic type T:
    //
    //     * a generic type cannot not be instantiated; that is, "new T()" is not allowed
    //     * T.class and "instanceof T" are not allowed
    //     * T cannot be used in a static context
    //
    // Generics allowed for a limited form of static type checking, which is not even enforced. Furthermore,
    // many of the constructs are unnatural. And some constructs that would be natural are not allowed,
    // or do not mean what they naturally would.
    //
    // All said, generics are, as I read on the Internet, an abomination.

    public V[] data;

    @SuppressWarnings("unchecked")
    public Tuple(int n) {
        data = (V[]) new Object[n];
    }

    @SafeVarargs
    public Tuple(V... values) {
        data = values;
    }

    public V get(int i) {
        return data[i];
    }

    public void put(int i, V elem) {
        data[i] = elem;
    }

    @SafeVarargs
    public static <T> Tuple T(T... values) {
        return new Tuple<>(values);
    }
}
