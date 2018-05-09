package lib.data_structures.list;

import lib.data_structures.list.link.SingleLink;
import lib.java_lang_extensions.parametrized_types.Constructor;
import lib.tokens.enums.CopyType;

public class LinkedList<T extends Constructor<T>>
    extends LinkedListBase<T, SingleLink<T>, LinkedList<T>>
{
    public LinkedList() {
        super();
    }

    @SafeVarargs
    public LinkedList(T... elems) {
        super(elems);
    }

    public LinkedList(LinkedList<T> list, CopyType copy_type) {
        super(list, copy_type);
    }

    // =========================================================================================

    public LinkedList<T> new_instance(Object... args) {
        return Constructor.new_instance(LinkedList.class, args);
    }

    public SingleLink<T> new_link(T elem) {
        return new SingleLink<>(elem);
    }

    // =========================================================================================

    public LinkedList<T> self() {
        return this;
    }
}
