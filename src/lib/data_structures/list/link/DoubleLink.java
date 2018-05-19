package lib.data_structures.list.link;

import lib.java_lang_extensions.parametrized_types.Copyable;
import lib.tokens.enums.CopyType;

public class DoubleLink<T extends Copyable<T>>
        extends LinkLegacy<T, DoubleLink<T>>
{
    public DoubleLink<T> prev;

    // =========================================================================================

    public DoubleLink() {
        super();
        prev = null;
    }

    public DoubleLink(T elem) {
        super(elem);
        prev = null;
    }

    public DoubleLink(T elem, DoubleLink<T> prev, DoubleLink<T> next) {
        super(elem, next);
        this.prev = prev;
    }

    public DoubleLink(DoubleLink<T> link, CopyType copy_type) {
        super(link, copy_type);
        this.prev = link.prev;
    }
}
