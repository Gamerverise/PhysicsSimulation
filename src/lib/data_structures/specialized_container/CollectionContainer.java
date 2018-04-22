package lib.data_structures.specialized_container;

import lib.data_structures.Copyable;
import lib.tokens.enums.CopyType;

import java.util.Collection;

public abstract class CollectionContainer<T extends Copyable<T>, D extends Collection<T>>
    extends CollectionAdapterContainer<T, D>
{
    public CollectionContainer() {
        super();
    }

    public CollectionContainer(Iterable<T> data_src, CopyType copy_type) {
        super(data_src, copy_type);
    }
}
