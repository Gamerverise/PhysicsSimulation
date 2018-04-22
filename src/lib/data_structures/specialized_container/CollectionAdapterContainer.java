package lib.data_structures.specialized_container;

import lib.data_structures.old.AdapterContainer;
import lib.tokens.enums.CopyType;

import java.util.Collection;

public abstract class CollectionAdapterContainer<T, D extends Collection<T>>
    extends AdapterContainer<T, D>
{
    public D collection;

    public CollectionAdapterContainer() {
        super();
    }

    public CollectionAdapterContainer(Iterable<T> data_src, CopyType copy_type) {
        super(data_src, copy_type);
    }

    public void add_item(T item) {
        collection.add(item);
    }

    public D get_underlying_data_structure() {
        return collection;
    }
}
