package lib.data_structures.list.link;

import lib.java_lang_extensions.parametrized_types.Constructable;
import lib.tokens.enums.CopyType;

public class SingleLinkX
        <T extends Constructable<T>>
        extends
        LinkBase<T, SingleLinkX<T>>
{
    public SingleLinkX() {}

    public SingleLinkX(T elem, SingleLinkX<T> next) {
        super(elem, next);
    }

    public SingleLinkX(T elem, CopyType copy_type) {
        super(elem, copy_type);
    }

    // =========================================================================================

    public SingleLinkX<T> new_instance(Object... args) {
        return Constructable.new_instance(getClass(), args);
    }

    public SingleLinkX<T> new_instance(SingleLinkX<T> link, CopyType copy_type) {
        return new SingleLinkX<>(link.elem, copy_type);
    }

    public SingleLinkX<T> new_copy(CopyType copy_type) {
        return new SingleLinkX<>(this.elem, copy_type);
    }
}
