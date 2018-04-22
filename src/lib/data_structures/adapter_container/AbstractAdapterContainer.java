package lib.data_structures.adapter_container;

import lib.data_structures.Copy;
import lib.data_structures.Copyable;
import lib.tokens.enums.CopyType;

public abstract class AbstractAdapterContainer<T>
        extends Copyable<AbstractAdapterContainer<T>>
        implements Iterable<T>
{
    public AbstractAdapterContainer() {
        init_underlying_data_structure();
    }

    public AbstractAdapterContainer(Iterable<T> data_src, CopyType copy_type) {
        this();
        add_copies(data_src, copy_type);
    }

    public AbstractAdapterContainer<T> new_copy(CopyType copy_type) {
        AbstractAdapterContainer<T> copy = super.new_copy(copy_type);
        copy.add_copies(this, copy_type);
        return copy;
    }

    public void add_copies(Iterable<T> data_src, CopyType copy_type) {
        for (T datum : data_src)
            add_item(Copy.new_copy(datum));
    }

    public abstract void init_underlying_data_structure();

    public abstract void add_item(T item);

    public abstract <D> D get_underlying_data_structure();

    public abstract <D> D cast();
}
