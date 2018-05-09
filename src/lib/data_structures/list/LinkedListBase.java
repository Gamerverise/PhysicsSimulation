package lib.data_structures.list;

import lib.data_structures.list.iter.LinkedListIterator;
import lib.data_structures.list.link.LinkBase;
import lib.java_lang_extensions.parametrized_types.Constructor;
import lib.java_lang_extensions.parametrized_types.ConstructorBaseDataStructure;
import lib.java_lang_extensions.parametrized_types.ConstructorDataStructure;
import lib.tokens.enums.CopyType;

import java.util.Iterator;

public abstract
class LinkedListBase
        <T extends Constructor<T>,
                LINK_TYPE extends LinkBase<T, LINK_TYPE>,
                LIST_TYPE extends LinkedListBase<T, LINK_TYPE, LIST_TYPE>>
        extends
        ConstructorBaseDataStructure<T, LIST_TYPE>
        implements
        Iterable<LINK_TYPE>
{
    public LINK_TYPE head;
    public LINK_TYPE tail;
    public int length;

    public LinkedListBase() {
        head = null;
        tail = null;
        length = 0;
    }

    public LinkedListBase(T... elems) {
        this();

        for(T elem : elems)
            append(elem);
    }

    public LinkedListBase(LinkedListBase<T, LINK_TYPE, LIST_TYPE> list, CopyType copy_type) {
        this();

        for(LINK_TYPE link : list)
            append(link.elem.new_copy(copy_type));
    }

    // =========================================================================================

    public abstract LINK_TYPE new_link(T elem);

    // =========================================================================================

    public void append(T elem) {
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
        return new LinkedListIterator<>(self());
    }
}
