package lib.data_structures.list.link;

import lib.java_lang_extensions.parametrized_class.Constructable;
import lib.tokens.enums.CopyType;

public class SingleLink<T extends Constructable<T>>
    extends LinkBase<T, SingleLink<T>>
{
    public SingleLink() {}

    public SingleLink(T elem, SingleLink<T> next) {
        super(elem, next);
    }

    public SingleLink(T elem, CopyType copy_type) {
        super(elem, copy_type);
    }

    // =========================================================================================

    public SingleLink<T> new_instance() {
        return new SingleLink<>();
    }

    public SingleLink<T> new_instance(SingleLink<T> link, CopyType copy_type) {
        return new SingleLink<>(link.elem, copy_type);
    }

    public SingleLink<T> new_copy(CopyType copy_type) {
        return new SingleLink<>(this.elem, copy_type);
    }
}
