package lib.data_structures.container;

import lib.java_lang_extensions.parametrized_class.Constructable;
import lib.tokens.enums.CopyType;

import java.util.Collection;
import java.util.Iterator;

public abstract class ContainerCopyableData
        <DT extends Constructable<DT>, DS extends Collection<DT>>
    extends ContainerAdapter<DT, DS>
{
    public ContainerCopyableData() {}

    public ContainerCopyableData(DS contents) {
        super(contents);
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
