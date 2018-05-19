package lib.data_structures.list.link;

import lib.java_lang_extensions.parametrized_types.Copyable;
import lib.tokens.enums.CopyType;

public class Link<T extends Copyable<T>>
        extends LinkLegacy<T, Link<T>>
{
    public Link() {
        super();
    }

    public Link(T elem) {
        super(elem);
    }

    public Link(T elem, Link<T> next) {
        super(elem, next);
    }

    public Link(Link<T> link, CopyType copy_type)
    {
        elem = link.elem.new_copy(copy_type);
        next = link.next;
    }
}
