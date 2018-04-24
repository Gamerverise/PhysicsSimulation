package lib.data_structures.container;

import lib.data_structures.Copyable;
import lib.tokens.enums.CopyType;

public abstract class Container
        <DT extends CDT, CDT extends Copyable<DT, CDT>,
                DS extends CDS, CDS extends UnderlyingDataStructure<DT, DS, CDS>>
    extends ContainerCopyableDataStructure<DT, DS, CDS>
{
    public Container() {
        super();
    }

    public Container(Iterable<DT> data_src, CopyType copy_type) {
        super(data_src, copy_type);
    }

    public void add_item(DT item) {
        underlying_data_structure.add_item(item);
    }

    public void add_copies(Iterable<DT> data_src, CopyType copy_type) {
        for (DT datum : data_src)
            add_item_copy(datum, copy_type);
    }
}
