package lib.data_structures;

import lib.data_structures.adapter.AdapterContainer;
import lib.debug.MethodNameHack;
import lib.tokens.enums.CopyType;

import java.util.AbstractCollection;

import static lib.debug.AssertMessages.BAD_CODE_PATH;
import static lib.debug.Debug.assert_msg;

public abstract class CollectionContainer<T extends Copyable<T>, D extends AbstractCollection<Copyable<T>>>
    extends AdapterContainer<T>
{
    public D data_structure;

    public CollectionContainer(Iterable<T> data_src, CopyType copy_type) {
        super(data_src, copy_type);
    }

    public void init_underlying_data_structure() {
        try {
            data_structure = getClass().newInstance();

        } catch (IllegalAccessException | InstantiationException e) {

            assert false : assert_msg(
                    this.getClass(),
                    new MethodNameHack() {}.method_name(),
                    BAD_CODE_PATH);
        }
    }

    public void add_item(Copyable<T> item) {
        data_structure.add(item);
    }

    public D get_underlying_data_structure() {
        return data_structure;
    }

    @SuppressWarnings("unchecked")
    public <E> E cast() {
        return (E) data_structure;
    }
}
