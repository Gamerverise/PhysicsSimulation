package lib.data_structures.list.iter;

import lib.data_structures.list.LinkedListLegacy;
import lib.data_structures.list.link.LinkLegacy;

import java.util.Iterator;

public class LinkedListIterator
        <T, LINK_TYPE extends LinkLegacy<T, LINK_TYPE>>
        implements
        Iterator<LINK_TYPE>
{
    public LINK_TYPE cur;

    public LinkedListIterator(
            LinkedListLegacy<T, LINK_TYPE, ? extends LinkedListLegacy<T, LINK_TYPE, ?>>
                    list)
    {
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
