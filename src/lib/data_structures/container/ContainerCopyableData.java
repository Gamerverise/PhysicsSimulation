package lib.data_structures.container;

import lib.data_structures.Copyable;
import lib.tokens.enums.CopyType;

import java.util.Collection;
import java.util.Iterator;

public abstract class ContainerCopyableData
        <DT extends Copyable<DT>, DS extends Collection<DT>>
    extends ContainerAdapter<DT, DS>
{
    public ContainerCopyableData() {}

    public ContainerCopyableData(DS contents) {
        super(contents);
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
