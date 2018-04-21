package lib.data_structures.adapter;

import lib.data_structures.Copyable;
import lib.tokens.enums.CopyType;

import java.util.Iterator;

public abstract class AdapterAbstractContainer<T>
        extends Copyable<T>
        implements Iterable<T>
{
    public AdapterAbstractContainer() {
        init_underlying_data_structure();
    }

    public AdapterAbstractContainer(Iterable<T> data_src, CopyType copy_type) {
        this();
        add_copies(data_src, copy_type);
    }

    @SuppressWarnings("unchecked")
    public AdapterAbstractContainer<T> new_copy(CopyType copy_type) {
        AdapterAbstractContainer<T> copy = (AdapterAbstractContainer<T>) super.new_copy(copy_type);
        copy.add_copies(this, copy_type);
        return copy;
    }

    public void add_copies(Iterable<T> data_src, CopyType copy_type) {
        Iterator<T> iter = data_src.iterator();

        while (iter.hasNext())
            add_item(iter.next().new_copy(copy_type));
    }

    public abstract void init_underlying_data_structure();

    public abstract void add_item(T item);

    public abstract <D> D get_underlying_data_structure();

    public abstract <D> D cast();
}
