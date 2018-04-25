package lib.java_lang_extensions.parametrized_class;

import java.util.Iterator;

public class AtomicArrayIterator_double implements Iterator<Double> {

    int cur = 0;

    public double[] atoms;

    public AtomicArrayIterator_double(double... atoms) {
        this.atoms = atoms;
    }

    @Override
    public boolean hasNext() {
        return cur < atoms.length;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Double next() {
        return atoms[cur++];
    }
}
