package lib.data_structures.list.link;

import lib.java_lang_extensions.parametrized_types.Constructor;
import lib.tokens.enums.CopyType;

public class SingleLink<T extends Constructor<T>>
        extends
        LinkBase<T, SingleLink<T>>
{
    public SingleLink() {}

    public SingleLink(T elem) {
        super(elem);
    }

    public SingleLink(T elem, SingleLink<T> next) {
        super(elem, next);
    }

    public SingleLink(SingleLink<T> link, CopyType copy_type) {
        super(link, copy_type);
    }

    // =========================================================================================

    public SingleLink<T> new_instance(Object... args) {
        return Constructor.new_instance(SingleLink.class, args);
    }
}
