package lib.data_structures.container;

import lib.data_structures.Copyable;
import lib.tokens.enums.CopyType;

public abstract class Container
        <DT extends Copyable<DT>, DS extends UnderlyingDataStructure<DT> & Copyable<DS>>
    extends ContainerCopyableDataStructure<DT, DS>
{
    public Container() {
        super();
    }

    public Container(Iterable<DT> data_src, CopyType copy_type) {
        super(data_src, copy_type);
    }

    public void add_item_copy(DT item, CopyType copy_type) {
        add_item(item.new_copy(copy_type));
    }
}
