package lib.data_structures.adapter_container;

import lib.tokens.enums.CopyType;

public abstract class AdapterContainer<T, D>
    extends AbstractAdapterContainer<T>
{
    public D data_structure;

    public AdapterContainer() {
        super();
    }

    public AdapterContainer(Iterable<T> data_src, CopyType copy_type) {
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
