package lib.data_structures.container;

import lib.java_lang_extensions.parametrized_types.ConstructableBase;
import lib.tokens.enums.CopyType;

import java.util.Iterator;

public abstract class Container<DT extends ConstructableBase<DT>>
    extends ContainerCopyableDataStructure<DT>
    implements ConstructableBase<Container<DT>>
{
    public Container(UnderlyingDataStructure<DT> data_src, CopyType copy_type) {
        super(data_src, copy_type);
    }

    public void add_data(Iterator<DT> data_src, CopyType copy_type) {
        switch (copy_type) {
            case COPY_SHALLOW:
                while (data_src.hasNext())
                    this.contents.add(data_src.next());
                break;
            case COPY_DEEP:
                while (data_src.hasNext())
                    this.contents.add(data_src.next().new_copy(copy_type));
                break;
        }
    }
}
