package lib.data_structures.container;

import lib.data_structures.Copyable;
import lib.tokens.enums.CopyType;

public abstract class HalfContainer<T extends Copyable<T>, D>
    extends AdapterContainer<T, D>
{
    public D underlying_data_structure;

    public HalfContainer() {
        super();
    }

    public HalfContainer(Iterable<T> data_src, CopyType copy_type) {
        super(data_src, copy_type);
    }

    public void add_item_copy(T item, CopyType copy_type) {
        add_item(item.new_copy(copy_type));
    }

    public D get_underlying_data_structure() {
        return underlying_data_structure;
    }
}
