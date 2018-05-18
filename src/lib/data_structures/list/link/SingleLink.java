package lib.data_structures.list.link;

import lib.java_lang_extensions.parametrized_types.Copyable;
import lib.java_lang_extensions.parametrized_types.Instantiator;
import lib.tokens.enums.CopyType;

public class SingleLink<T extends SingleLink<T>>
        extends Copyable<T>
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

    public SingleLink<T> self() {
        return this;
    }

    public SingleLink<T> new_instance(Object... args) {
        return Instantiator.new_instance(SingleLink.class, args);
    }
}
