package lib.data_structures;

import lib.tokens.enums.CopyType;

import java.util.Iterator;

public abstract class Container<T extends Containable, D>
    extends ContainerDataStructure
        implements Iterable<T>
{
    public D data_structure;
    
    public Container() {
        data_structure = new_data_structure();
    }

    public Container(D data_structure, CopyType copy_type) {
        set_data_structure(copy_data_structure(copy_type));
    }

    public Container(Container<T, D> data_structure, CopyType copy_type) {
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

    public abstract void add_item(T item);


    public abstract D new_data_structure();

    public abstract D copy_data_structure(CopyType copy_type);
    
    public void set_data_structure(D data_structure) {
        this.data_structure = data_structure;
    }

    public D get_data_structure() {
        return data_structure;
    }

    
    @SuppressWarnings("unchecked")
    public <E> E cast_data_structure() {
        return (E) get_data_structure();
    }
}
