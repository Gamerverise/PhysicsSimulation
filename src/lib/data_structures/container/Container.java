package lib.data_structures.container;

import lib.java_lang_extensions.parametrized_types.Constructor;
import lib.tokens.enums.CopyType;

import java.util.Iterator;

public abstract class
Container
        <DT extends Constructor<DT>,
                UDS extends ContainerUnderlyingDataStructure<DT, UDS>,
                DS extends Container<DT, UDS, DS>>
    extends ContainerCopyableDataStructure<DT, UDS, DS>
{
    @SafeVarargs
    public Container(DT... elems) {
        for (DT elem : elems)
            contents.add(elem);
    }

    public Container(DS container, CopyType copy_type)
    {
        contents = container.contents.new_copy(copy_type);
    }
}
