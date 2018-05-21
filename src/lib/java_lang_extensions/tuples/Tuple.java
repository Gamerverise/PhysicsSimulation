package lib.java_lang_extensions.tuples;

import lib.java_lang_extensions.parametrized_types.Copyable;

public class Tuple extends Copyable<Tuple>
{
    public Object[] elems;

    public Tuple(Object... elems) {
        this.elems = elems;
    }

    @SuppressWarnings("unchecked")
    public <T> T get(int i) {
        return (T) elems[i];
    }
}
