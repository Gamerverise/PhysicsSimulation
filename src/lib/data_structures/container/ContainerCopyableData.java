package lib.data_structures.container;

import lib.data_structures.Copyable;
import lib.tokens.enums.CopyType;

public abstract class ContainerCopyableData
        <DT extends CDT, CDT extends Copyable<DT, CDT>, DS>
    extends AdapterContainer<DT, DS>
{
    public DS underlying_data_structure;

    public ContainerCopyableData() {
        super();
    }

    public ContainerCopyableData(Iterable<DT> data_src, CopyType copy_type) {
        super(data_src, copy_type);
    }

    public void add_item_copy(DT item, CopyType copy_type) {
        add_item(item.new_copy(copy_type));
    }

    public DS get_underlying_data_structure() {
        return underlying_data_structure;
    }
}
