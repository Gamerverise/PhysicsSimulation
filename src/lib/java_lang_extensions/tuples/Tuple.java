package lib.java_lang_extensions.tuples;

import lib.java_lang_extensions.parametrized_types.Copyable;

public class Tuple<T> extends Copyable<Tuple<T>>
{
    public T[] elems;

    public Tuple(T... elems) {
        this.elems = elems;
    }

    @SuppressWarnings("unchecked")
    public T get(int i) {
        return (T) elems[i];
    }

    @SuppressWarnings("unchecked")
    public <U>
    U gett(int i) {
        return (U) elems[i];
    }
}
