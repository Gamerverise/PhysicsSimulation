package lib.data_structures.list;

import lib.data_structures.list.iter.LinkedListIterator;
import lib.data_structures.list.link.LinkBase;
import lib.java_lang_extensions.parametrized_types.Constructor;
import lib.tokens.enums.CopyType;

import java.util.Iterator;

public abstract
class LinkedListAbstract
        <T extends Constructor<T>,
                LINK_TYPE extends LinkBase<T, LINK_TYPE>>
        implements
        Iterable<LINK_TYPE>
{
    public LINK_TYPE head;
    public LINK_TYPE tail;
    public int length;

    public LinkedListAbstract() {
        head = null;
        tail = null;
        length = 0;
    }

    @SafeVarargs
    public LinkedListAbstract(T... elems) {
        this();

        for(T elem : elems)
            append(elem);
    }

    public LinkedListAbstract(LinkedListAbstract<T, LINK_TYPE> list, CopyType copy_type) {
        this();

        for(LINK_TYPE link : list)
            append(link.elem.new_copy(copy_type));
    }

    // =========================================================================================

    public abstract LinkedListAbstract<T, LINK_TYPE> new_copy_abstract(CopyType copy_type);

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

    public Iterator<LINK_TYPE> iterator() {
        return new LinkedListIterator<>(this);
    }
}
