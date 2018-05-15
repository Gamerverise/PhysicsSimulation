package lib.data_structures.list;

import lib.data_structures.list.link.LinkBase;
import lib.java_lang_extensions.parametrized_types.Instantiator;
import lib.tokens.enums.CopyType;

public abstract
class LinkedListQualified
        <T extends Instantiator<T>,
                LINK_TYPE extends LinkBase<T, LINK_TYPE>,
                LIST_TYPE extends LinkedListQualified<T, LINK_TYPE, LIST_TYPE>>
        extends
        LinkedListAbstract<T, LINK_TYPE>
        implements
        Instantiator<LIST_TYPE>, Iterable<LINK_TYPE>
{
    public LinkedListQualified() {
        super();
    }

    @SafeVarargs
    public LinkedListQualified(T... elems) {
        super(elems);
    }

    public LinkedListQualified(LIST_TYPE list, CopyType copy_type) {
        super(list, copy_type);
    }

    // =========================================================================================

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
}
