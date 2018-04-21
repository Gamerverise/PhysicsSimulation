package lib.data_structures;

import lib.tokens.enums.CopyType;

import java.util.Iterator;

import static lib.tokens.enums.CopyType.COPY_DEEP;
import static lib.tokens.enums.CopyType.COPY_SHALLOW;

public abstract class AbstractContainerDataStructure<T extends Containable> implements Iterable<T> {

    public abstract void add_item(T item);

//    public abstract void add_item(Containable<T> item);

    public abstract <D> D cast();

    public AbstractContainerDataStructure(Iterable<T> data_structure, CopyType copy_type) {
        Iterator<T> iter = data_structure.iterator();

        if (copy_type == COPY_SHALLOW)
            while (iter.hasNext())
                add_item(iter.next());
        else
            while (iter.hasNext())
                add_item(iter.next().new_copy(COPY_DEEP));
    }

    public AbstractContainerDataStructure(Container<T, D> data_structure, CopyType copy_type) {
        Iterator<T> iter = iterator();
        while (iter.hasNext())
            add_item(iter.next());
    }

    public Container<T, D> new_copy(CopyType copy_type) {
        Container<T, D> container = new_empty();

        ContainerDataStructure<T, D> data_structure = container.get_data_structure();
        Iterator<T> iter = get_data_structure().iterator();

        while (iter.hasNext())
            data_structure.add_item(iter.next().new_copy(copy_type));

        return container;
    }

//    public void copy_in(Container<T> container, CopyType copy_type) {
//        switch (copy_type) {
//            case COPY_SHALLOW:
//                set_data_structure(container.get_data_structure());
//                break;
//            case COPY_DEEP:
//                set_data_structure(container.get_data_structure().new_copy(copy_type);
//        }
//    }

    public abstract Container<T, D> new_empty();


    public abstract D new_data_structure();

    public abstract D copy_data_structure(CopyType copy_type);

    public void set_data_structure(D data_structure) {
        this.data_structure = data_structure;
    }

    public D get_data_structure() {
        return data_structure;
    }
}
