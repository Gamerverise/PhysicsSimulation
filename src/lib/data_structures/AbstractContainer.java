package lib.data_structures;

import lib.tokens.enums.CopyType;

import java.util.Iterator;

public abstract class AbstractContainer<T extends Containable, D>
        implements Iterable<T>
{
    public AbstractContainer() {
        set_data_structure(new_data_structure());
    }

    public AbstractContainer(D data_structure) {
        set_data_structure(data_structure);
    }

    public AbstractContainer(AbstractContainer<T, D> data_structure, CopyType copy_type) {
        Iterator<T> iter = iterator();
        while (iter.hasNext())
            add_item(iter.next());
    }

    public AbstractContainer<T, D> new_copy(CopyType copy_type) {
        AbstractContainer<T, D> container = new_empty();

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

    public abstract AbstractContainer<T, D> new_empty();

    public abstract void add_item(T item);


    public abstract D new_data_structure();

    public abstract void set_data_structure(D data_structure);

    public abstract D get_data_structure();

    @SuppressWarnings("unchecked")
    public <D> D cast_data_structure() {
        return (D) get_data_structure();
    }
}
