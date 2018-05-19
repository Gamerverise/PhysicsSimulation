package lib.data_structures.list;

import lib.data_structures.list.iter.LinkedListIterator;
import lib.data_structures.list.link.LinkLegacy;
import lib.java_lang_extensions.parametrized_types.Copyable;
import lib.tokens.enums.CopyType;

public abstract
class LinkedListLegacy
        <T,
                LINK_TYPE extends LinkLegacy<T, LINK_TYPE>,
                LIST_TYPE extends LinkedListLegacy<T, LINK_TYPE, LIST_TYPE>>
        extends
        Copyable<LIST_TYPE>
        implements
        Iterable<LINK_TYPE>
{
    public LINK_TYPE head;
    public LINK_TYPE tail;
    public int length;

    public LinkedListLegacy() {
        head = null;
        tail = null;
        length = 0;
    }

    @SafeVarargs
    public LinkedListLegacy(T... elems) {
        this();

        for(T elem : elems)
            append(elem);
    }

    public LinkedListLegacy(LIST_TYPE list, CopyType copy_type) {
        this();

        for(LinkLegacy<T, ? extends LinkLegacy<T, ?>> link : list)
            append(link.elem);
    }

    // =========================================================================================

    public abstract LINK_TYPE new_link(T elem);

    public abstract LIST_TYPE self();

    // =========================================================================================

    public void append(T elem)
    {
        append(new_link(elem));
    }

    public void append(LINK_TYPE link) {
        link.next = null;

        if (length == 0)
            head = link;
        else
            tail.next = link;

        tail = link;

        length += 1;
    }

    public LIST_TYPE split(int n) {

        if (n <= 0)
            return self();

        if (n >= length)
            return new_instance();

        LINK_TYPE new_tail = head;

        for (int i = 1; i < n; i++)
            new_tail = new_tail.next;

        LIST_TYPE end_part = new_instance();
        end_part.head = new_tail.next;
        end_part.tail = tail;
        end_part.length = length - n;

        tail = new_tail;
        tail.next = null;
        length = n;

        return end_part;
    }

    // =========================================================================================

    public LinkedListIterator<T, LINK_TYPE> iterator() {
        return new LinkedListIterator<>(this);
    }
}
