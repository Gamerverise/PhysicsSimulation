package lib.java_lang_extensions.var_var_args;

public class SubVarArgs<T> {

    public T[] array;                   // Yes, protected!

    @SafeVarargs
    private SubVarArgs(T... sub_var_args) {
        // private in order to force use of static constructor
        this.array = sub_var_args;
    }

    // FIXME: Is there any way to use this function without getting an "incompatible types" error?
    @SuppressWarnings("unchecked")
    public <V> V[] cast() {
        return (V[]) array;
    }

    @SuppressWarnings("unchecked")
    public <V> V cast(int i) {
        return (V) array[i];
    }

    public Class type_ish() {
        if (array.length >= 1)
            return array[0].getClass();

        return null;
    }

    public Class type(int i) {
        return array[i].getClass();
    }

    @SafeVarargs
    public static <V> SubVarArgs<V> V(V... sub_var_args) {
        return new SubVarArgs<>(sub_var_args);
    }
}
