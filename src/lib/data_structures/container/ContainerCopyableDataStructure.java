package lib.data_structures.container;

import lib.tokens.enums.CopyType;

public abstract class ContainerCopyableDataStructure<DT>
    extends ContainerAdapter<DT, UnderlyingDataStructure<DT>>
{
    public ContainerCopyableDataStructure(UnderlyingDataStructure<DT> contents) {
        super(contents);
    }

    public ContainerCopyableDataStructure(UnderlyingDataStructure<DT> data_src, CopyType copy_type) {
        contents = data_src.new_copy(copy_type);
    }
}
