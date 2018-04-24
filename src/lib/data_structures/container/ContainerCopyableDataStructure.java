package lib.data_structures.container;

import lib.data_structures.Copyable;
import lib.tokens.enums.CopyType;

public abstract class ContainerCopyableDataStructure
        <DT, DS extends CDS, CDS extends UnderlyingDataStructure<DT, DS, CDS>>
    extends AdapterContainer<DT, DS>
{
    public DS underlying_data_structure;

    public ContainerCopyableDataStructure() {
        super();
    }

    public ContainerCopyableDataStructure(Iterable<DT> data_src, CopyType copy_type) {
        super(data_src, copy_type);
    }

    public DS get_underlying_data_structure() {
        return underlying_data_structure;
    }
}
