package lib.data_structures.container;

import lib.data_structures.Copyable;
import lib.tokens.enums.CopyType;

public abstract class Container<T extends Copyable<T>, D extends UnderlyingDataStructure<T>>
    extends HalfContainer<T, D>
{
    public Container() {
        super();
    }

    public Container(Iterable<T> data_src, CopyType copy_type) {
        super(data_src, copy_type);
    }

    public void add_item(T item) {
        underlying_data_structure.add_item(item);
    }

    public void add_copies(Iterable<T> data_src, CopyType copy_type) {
        for (T datum : data_src)
            add_item_copy(datum, copy_type);
    }
}
