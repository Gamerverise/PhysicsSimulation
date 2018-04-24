package lib.data_structures.specialized_container;

import lib.data_structures.Copyable;
import lib.tokens.enums.CopyType;

import java.util.Collection;

public abstract class CollectionContainer<DT extends Copyable<DT>, DS extends Collection<DT>>
    extends CollectionContainerAdapter<DT, DS>
{
    public CollectionContainer() {
        super();
    }

    public CollectionContainer(Iterable<DT> data_src, CopyType copy_type) {
        super(data_src, copy_type);
    }
}