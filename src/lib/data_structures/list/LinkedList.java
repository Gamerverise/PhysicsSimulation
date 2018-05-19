package lib.data_structures.list;

import lib.data_structures.list.link.Link;
import lib.java_lang_extensions.parametrized_types.Copyable;
import lib.tokens.enums.CopyType;

public class LinkedList<T extends Copyable<T>>
        extends LinkedListBase<T, Link<T>, LinkedList<T>>
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

    public LinkedList<T> self() {
        return this;
    }

    public Link<T> new_link(T elem) {
        return new Link<>(elem);
    }
}
