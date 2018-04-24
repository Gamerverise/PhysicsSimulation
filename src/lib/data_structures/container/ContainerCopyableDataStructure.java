package lib.data_structures.container;

import lib.data_structures.Copyable;
import lib.tokens.enums.CopyType;

public abstract class ContainerCopyableDataStructure
        <DT, DS extends UnderlyingDataStructure<DT> & Copyable<DS>>
    extends ContainerAdapter<DT, DS>
{
    public ContainerCopyableDataStructure() {
        super();
    }

    public ContainerCopyableDataStructure(Iterable<DT> data_src, CopyType copy_type) {
        super(data_src, copy_type);
    }

    public void add_item(DT item) {
        contents.add_item(item);
    }

    public void add_copies(Iterable<DT> data_src, CopyType copy_type) {
        for (DT datum : data_src)
            add_item_copy(datum, copy_type);
    }
}
