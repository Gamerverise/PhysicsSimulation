package lib.data_structures.specialized_container;

import lib.data_structures.container.ContainerAdapter;
import lib.tokens.enums.CopyType;

import java.util.Collection;

public abstract class CollectionContainerAdapter<DT, DS extends Collection<DT>>
    extends ContainerAdapter<DT, DS>
{
    public DS collection;

    public CollectionContainerAdapter() {
        super();
    }

    public CollectionContainerAdapter(Iterable<DT> data_src, CopyType copy_type) {
        super(data_src, copy_type);
    }

    public void add_item(DT item) {
        collection.add(item);
    }

    public DS get_underlying_data_structure() {
        return collection;
    }
}
