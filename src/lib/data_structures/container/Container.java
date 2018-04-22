package lib.data_structures.container;

import lib.data_structures.Copyable;
import lib.tokens.enums.CopyType;

public abstract class Container<T extends Copyable<T>, D>
    extends AbstractContainer<T>
{
    public D data_structure;

    public Container() {
        super();
    }

    public Container(Iterable<T> data_src, CopyType copy_type) {
        super(data_src, copy_type);
    }

    public D get_underlying_data_structure() {
        return data_structure;
    }

    @SuppressWarnings("unchecked")
    public <E> E cast() {
        return (E) data_structure;
    }
}
