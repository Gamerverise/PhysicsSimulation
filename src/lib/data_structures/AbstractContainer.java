package lib.data_structures;

import lib.data_structures.adapter.AdapterAbstractContainer;
import lib.tokens.enums.CopyType;

import java.util.Iterator;

public abstract class AbstractContainer<T extends Copyable<T>>
    extends AdapterAbstractContainer<T>
{
    public AbstractContainer() {
        init_underlying_data_structure();
    }

    public AbstractContainer(Iterable<T> data_src, CopyType copy_type) {
        this();
        add_copies(data_src, copy_type);
    }

    @SuppressWarnings("unchecked")
    public AbstractContainer<T> new_copy(CopyType copy_type) {
        AbstractContainer<T> copy = (AbstractContainer<T>) super.new_copy(copy_type);
        copy.add_copies(this, copy_type);
        return copy;
    }

    public void add_copies(Iterable<T> data_src, CopyType copy_type) {
        Iterator<T> iter = data_src.iterator();

        while (iter.hasNext())
            add_item(iter.next().new_copy(copy_type));
    }

    public abstract void init_underlying_data_structure();

    public abstract void add_item(Copyable<T> item);

    public abstract <D> D get_underlying_data_structure();

    public abstract <D> D cast();
}
