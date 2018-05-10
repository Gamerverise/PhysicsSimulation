package lib.data_structures.container;

import lib.tokens.enums.CopyType;

public abstract class
ContainerCopyableDataStructure
        <DT,
                UDS extends ContainerUnderlyingDataStructure<DT, UDS>,
                DS extends ContainerCopyableDataStructure<DT, UDS, DS>>
    extends
        ContainerAdapter<DT, UDS, DS>
{
    @SafeVarargs
    public ContainerCopyableDataStructure(DT... elems) {
        for (DT elem : elems)
            contents.add(elem);
    }

    public ContainerCopyableDataStructure(DS container, CopyType copy_type)
    {
        contents = container.contents.new_copy(copy_type);
    }
}
