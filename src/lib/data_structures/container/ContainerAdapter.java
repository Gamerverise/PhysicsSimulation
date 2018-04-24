package lib.data_structures.container;

import lib.java_lang_extensions.parametrized_class.ParametrizedClass;
import lib.tokens.enums.CopyType;

public abstract class ContainerAdapter
        <DATA_TYPE, UNDERLYING_DATA_STRUCTURE>
        implements Iterable<DATA_TYPE>
{
    public UNDERLYING_DATA_STRUCTURE contents;

    public ContainerAdapter() {
        init_underlying_data_structure();
    }

    public ContainerAdapter(Iterable<DATA_TYPE> data_src, CopyType copy_type) {
        this();
        add_copies(data_src, copy_type);
    }

    public ContainerAdapter<DATA_TYPE, UNDERLYING_DATA_STRUCTURE>
    new_copy(CopyType copy_type) {
        
        ContainerAdapter<DATA_TYPE, UNDERLYING_DATA_STRUCTURE>
                new_instance = ParametrizedClass.new_instance(this.getClass());
       
        new_instance.add_copies(this, copy_type);
       
        return new_instance;
    }

    public abstract void add_item(DATA_TYPE item);

    public abstract void add_item_copy(DATA_TYPE item, CopyType copy_type);

    public void add_copies(Iterable<DATA_TYPE> data_src, CopyType copy_type) {
        for (DATA_TYPE datum : data_src)
            add_item_copy(datum, copy_type);
    }

    public abstract void init_underlying_data_structure();

    public <D> D cast() {
        return (D) contents;
    }
}
