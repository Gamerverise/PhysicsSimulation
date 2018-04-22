package lib.data_structures.container;

import lib.data_structures.Copyable;
import lib.java_lang_extensions.parametrized_class.ParametrizedClass;
import lib.tokens.enums.CopyType;

public abstract class AdapterContainer<T, D>
        implements Copyable<AdapterContainer<T, D>>, Iterable<T>
{
    public AdapterContainer() {
        init_underlying_data_structure();
    }

    public AdapterContainer(Iterable<T> data_src, CopyType copy_type) {
        this();
        add_copies(data_src, copy_type);
    }

    public AdapterContainer<T, D> new_copy(CopyType copy_type) {
        AdapterContainer<T, D> new_instance = ParametrizedClass.new_instance(this.getClass());
        new_instance.add_copies(this, copy_type);
        return new_instance;
    }

    public abstract void add_item(T item);

    public abstract void add_item_copy(T item, CopyType copy_type);

    public void add_copies(Iterable<T> data_src, CopyType copy_type) {
        for (T datum : data_src)
            add_item_copy(datum, copy_type);
    }

    public abstract void init_underlying_data_structure();

    public abstract D get_underlying_data_structure();

    public <D> D cast() {
        return (D) get_underlying_data_structure();
    }
}
