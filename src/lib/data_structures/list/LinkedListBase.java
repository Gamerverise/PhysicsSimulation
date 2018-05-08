package lib.data_structures.list;

import lib.data_structures.list.iter.LinkedListIterator;
import lib.data_structures.list.link.LinkBase;
import lib.java_lang_extensions.overload_constants.CopyTypeOverloadConstants;
import lib.java_lang_extensions.parametrized_types.Constructor;
import lib.tokens.enums.CopyType;

import java.util.Iterator;

import static lib.tokens.enums.CopyType.COPY_SHALLOW;

public abstract
class LinkedListBase
        <T extends Constructor<T>,
                LINK_TYPE extends LinkBase<T, LINK_TYPE>,
                LIST_TYPE extends LinkedListBase<T, LINK_TYPE, LIST_TYPE>>
        implements
        Constructor<LIST_TYPE>, Iterable<LINK_TYPE>
{
    public LINK_TYPE head;
    public LINK_TYPE tail;
    public int length;

    public LinkedListBase() {
        head = null;
        tail = null;
        length = 0;
    }

    public LinkedListBase(LinkedListBase<T, LINK_TYPE, LIST_TYPE> list, CopyType copy_type) {
        this();

        for(LINK_TYPE link : list)
            append(link.elem.new_copy(copy_type), copy_type);
    }

    // =========================================================================================

    public LIST_TYPE new_copy(CopyType copy_type) {
        return new_instance(this, copy_type);
    }

    public abstract LINK_TYPE new_link(T elem, CopyType copy_type);

    // =========================================================================================

    public void append(T elem, CopyType copy_type) {
        append(new_link(elem, copy_type));
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

    // =========================================================================================

    public abstract LIST_TYPE self();

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

    public Iterator<LINK_TYPE> iterator() {
        return new LinkedListIterator<>(this);
    }
}
