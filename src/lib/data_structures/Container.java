package lib.data_structures;

import lib.tokens.enums.CopyType;

public abstract class Container<T extends Copyable<T>, D extends AbstractContainer<T>>
    extends AbstractContainer<T>
{
    public D data_structure;

    public Container(Iterable<T> data_src, CopyType copy_type) {
        super(data_src, copy_type);
    }

    public void init_underlying_data_structure() {
        data_structure = new_copy();
    }

    public void add_item(Copyable<T> item) {
        data_structure.add(item);
    }

    public D get_underlying_data_structure() {
        return data_structure;
    }

    @SuppressWarnings("unchecked")
    public <E> E cast() {
        return (E) data_structure;
    }
}
