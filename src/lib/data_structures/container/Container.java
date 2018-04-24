package lib.data_structures.container;

import lib.data_structures.Copyable;
import lib.tokens.enums.CopyType;

import java.util.Iterator;

public abstract class Container<DT extends Copyable<DT>>
    extends ContainerCopyableDataStructure<DT>
    implements Copyable<Container<DT>>
{
    public Container(UnderlyingDataStructure<DT> data_src, CopyType copy_type) {
        super(data_src, copy_type);
    }

    public void add_data(Iterable<DT> data_src, CopyType copy_type) {
        Iterator<DT> iter = data_src.iterator();

        switch (copy_type) {
            case COPY_SHALLOW:
                while (iter.hasNext())
                    this.contents.add(iter.next());
                break;
            case COPY_DEEP:
                while (iter.hasNext())
                    this.contents.add(iter.next().new_copy(copy_type));
                break;
        }
    }
}
