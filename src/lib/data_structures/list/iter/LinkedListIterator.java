package lib.data_structures.list.iter;

import lib.data_structures.list.LinkedListBase;
import lib.data_structures.list.link.LinkBase;
import lib.java_lang_extensions.parametrized_types.Constructor;

import java.util.Iterator;

public class LinkedListIterator
        <T extends Constructor<T>,
                LINK_TYPE extends LinkBase<T, LINK_TYPE>,
                LIST_TYPE extends LinkedListBase<T, LINK_TYPE, LIST_TYPE>>
        implements
        Iterator<LINK_TYPE>
{
    public LINK_TYPE cur;

    public LinkedListIterator(LIST_TYPE list) {
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
