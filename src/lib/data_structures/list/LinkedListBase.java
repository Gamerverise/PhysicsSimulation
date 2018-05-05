package lib.data_structures.list;

import lib.java_lang_extensions.parametrized_class.Constructable;
import lib.data_structures.list.link.LinkBase;
import lib.tokens.enums.CopyType;

import java.util.Iterator;

import static lib.tokens.enums.CopyType.COPY_SHALLOW;

public abstract
class LinkedListBase
        <T extends CONSTRUCTABLE_TYPE,
                CONSTRUCTABLE_TYPE extends Constructable<T, CONSTRUCTABLE_TYPE>,
                LINK_TYPE extends LinkBase<CONSTRUCTABLE_TYPE, LINK_TYPE>,
                LIST_TYPE extends LinkedListBase<CONSTRUCTABLE_TYPE, LINK_TYPE, LIST_TYPE>>
        implements
        Constructable<LIST_TYPE, LinkedListBase<CONSTRUCTABLE_TYPE, LINK_TYPE, LIST_TYPE>>, Iterable<LINK_TYPE>
{
    public static class ListBaseIterator
            <CONSTRUCTABLE_TYPE extends T, T extends <CONSTRUCTABLE_TYPE, T>,
                    LINK_TYPE extends LinkBase<CONSTRUCTABLE_TYPE, LINK_TYPE>,
                    LIST_TYPE extends LinkedListBase<CONSTRUCTABLE_TYPE, LINK_TYPE, LIST_TYPE>>
            implements
            Iterator<LINK_TYPE>
    {
        public LINK_TYPE cur;

        public ListBaseIterator(LinkedListBase<T, LINK_TYPE, LIST_TYPE> list) {
            cur = list.head;
        }

        public boolean hasNext() {
            return cur.next != null;
        }

        public LINK_TYPE next() {
            cur = cur.next;
            return cur;
        }
    }

    public Class<LINK_TYPE> link_type;

    public LINK_TYPE head;
    public LINK_TYPE tail;
    public int length;

    public LinkedListBase() {
        head = null;
        tail = null;
        length = 0;
    }

    public LinkedListBase(LINK_TYPE link) {
        head = link;
        tail = link;
        int length = 0;

        while(tail != null) {
            tail = tail.next;
            length++;
        }
    }

    public LinkedListBase(LIST_TYPE list, CopyType copy_type) {
        this();

        for(LINK_TYPE link : list)
            append(link.elem.new_copy(copy_type));
    }

    public abstract LINK_TYPE new_link(T elem, CopyType copy_type);

    public void append(T elem) {
        tail.next = Constructable.new_instance(link_type, elem, COPY_SHALLOW);
        length += 1;
    }

    public void append(LINK_TYPE link) {
        link.next = null;
        tail.next = link;
        length += 1;
    }

    public LIST_TYPE split(int n) {
        if (length == 0 || n == 0 || n >= length)
            return null;

        LINK_TYPE new_tail = head;

        int i;
        for (i = 1; i < n; i++)
            new_tail = new_tail.next;

        LIST_TYPE list = Constructable.new_instance(this.getClass(), tail.next);
        new_tail.next = null;
        length = i;

        return list;
    }

    public LinkedListBase<T, LINK_TYPE, LIST_TYPE> new_copy(CopyType copy_type) {
        return Constructable.new_instance(this.getClass(), copy_type);
    }

    public ListBaseIterator<T, LINK_TYPE, LIST_TYPE> iterator() {
        return new ListBaseIterator<>(this);
    }
}
