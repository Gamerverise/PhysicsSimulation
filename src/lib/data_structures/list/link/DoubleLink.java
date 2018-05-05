package lib.data_structures.list.link;

import lib.java_lang_extensions.parametrized_types.Constructable;
import lib.java_lang_extensions.parametrized_types.ConstructableBase;
import lib.tokens.enums.CopyType;

public class DoubleLink
        <T extends Constructable<T>>
        extends
        LinkBase<T, DoubleLink<T>>
{
    public DoubleLink<T> prev;

    // =========================================================================================

    public DoubleLink() {
        super();
        prev = null;
    }

    public DoubleLink(T elem, DoubleLink<T> prev, DoubleLink<T> next) {
        super(elem, next);
        this.prev = prev;
    }

    public DoubleLink(T elem, CopyType copy_type) {
        super(elem, copy_type);
        this.prev = null;
    }

    // =========================================================================================

    public DoubleLink<T> new_instance() {
        return new DoubleLink<>();
    }

    public DoubleLink<T> new_instance(DoubleLink<T> link, CopyType copy_type) {
        return new DoubleLink<>(link.elem, copy_type);
    }

    public DoubleLink<T> new_copy(CopyType copy_type) {
        return new DoubleLink<>(this.elem, copy_type);
    }
}
