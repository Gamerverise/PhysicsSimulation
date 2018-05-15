package lib.data_structures.list.iter;

import lib.data_structures.list.LinkedListAbstract;
import lib.data_structures.list.link.LinkBase;
import lib.java_lang_extensions.parametrized_types.Instantiator;

import java.util.Iterator;

public class LinkedListIterator
        <T extends Instantiator<T>,
                LINK_TYPE extends LinkBase<T, LINK_TYPE>>
        implements
        Iterator<LINK_TYPE>
{
    public LINK_TYPE cur;

    public LinkedListIterator(LinkedListAbstract<T, LINK_TYPE> list) {
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
