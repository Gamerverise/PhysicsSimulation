package lib.data_structures.list.link;

import lib.java_lang_extensions.parametrized_types.Constructor;
import lib.tokens.enums.CopyType;

public class SingleLink
        <T extends Constructor<T>>
    extends
        LinkBase<T, SingleLink<T>>
{
    public SingleLink() {}

    public SingleLink(T elem, SingleLink<T> next) {
        super(elem, next);
    }

    public SingleLink(T elem, CopyType copy_type) {
        super(elem, copy_type);
    }

    // =========================================================================================

    public SingleLink<T> new_instance(Object... args) {
        return Constructor.new_instance(SingleLink.class, args);
    }

    public SingleLink<T> new_instance(SingleLink<T> link, CopyType copy_type) {
        return new SingleLink<>(link.elem, copy_type);
    }

    public SingleLink<T> new_copy(CopyType copy_type) {
        return new SingleLink<>(this.elem, copy_type);
    }
}
