package lib.java_lang_extensions.parametrized_class;

import java.util.Iterator;

public class AtomicArrayIterator<T> implements Iterator<T> {

    int cur = 0;

    public Object atoms;

    public <U> AtomicArrayIterator(U... atoms) {

    }

    @Override
    public boolean hasNext() {
        return cur < ((double[]) atoms.getClass().cast(atoms)).length;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T next() {
        return (T) (Double) ((double[]) atoms.getClass().cast(atoms))[cur++];
    }
}
