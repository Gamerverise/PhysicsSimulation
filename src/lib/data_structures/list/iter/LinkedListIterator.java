package lib.data_structures.list.iter;

import lib.data_structures.list.LinkedListLegacyBase;
import lib.data_structures.list.link.LinkLegacyBase;

import java.util.Iterator;

public class LinkedListIterator
        <T, LINK_TYPE extends LinkLegacyBase<T, LINK_TYPE>>
        implements
        Iterator<LINK_TYPE>
{
    public LINK_TYPE cur;

    public LinkedListIterator(
            LinkedListLegacyBase<T, LINK_TYPE, ? extends LinkedListLegacyBase<T, LINK_TYPE, ?>>
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
