package lib.data_structures.list;

import lib.data_structures.list.iter.LinkedListIterator;
import lib.data_structures.list.link.LinkLegacy;
import lib.data_structures.list.link.LinkLegacyBase;
import lib.java_lang_extensions.function_types.FunctionR1;
import lib.java_lang_extensions.parametrized_types.Copyable;
import lib.tokens.enums.CopyType;

public abstract
class LinkedListLegacyBase
        <T,
                LINK_TYPE extends LinkLegacyBase<T, LINK_TYPE>,
                LIST_TYPE extends LinkedListLegacyBase<T, LINK_TYPE, LIST_TYPE>>
        extends
        Copyable<LIST_TYPE>
        implements
        Iterable<LINK_TYPE>
{
    public LINK_TYPE head;
    public LINK_TYPE tail;
    public int length;

    public LinkedListLegacyBase() {
        head = null;
        tail = null;
        length = 0;
    }

    @SafeVarargs
    public LinkedListLegacyBase(T... elems) {
        this();

        for(T elem : elems)
            append(elem);
    }

    public LinkedListLegacyBase(LIST_TYPE list, CopyType copy_type) {
        this();

        for(LinkLegacyBase<T, ? extends LinkLegacyBase<T, ?>> link : list)
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

    public void prepend(T elem) {
        prepend(new_link(elem));
    }

    public void prepend(LINK_TYPE link) {
        link.next = head;

        head = link;

        if (length == 0)
            tail = link;

        length += 1;
    }

    public void push(T elem) {
        prepend(elem);
    }

    public T pop() {
        if (length == 0)
            return null;

        T tmp = head.elem;
        head = head.next;

        if (length == 1)
            tail = null;

        length -= 1;

        return tmp;
    }

    public T peek()
    {
        if (length > 0)
            return head.elem;

        return null;
    }

    public LINK_TYPE peek_link() {
        return head;
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

    public void truncate(LINK_TYPE link) {
        tail = link;
        link.next = null;
    }


    // =========================================================================================

    public String sprint(FunctionR1<String, T> sprint_function) {
        StringBuilder result = new StringBuilder();

        LinkedListIterator<T, LINK_TYPE> iter = iterator();

        if (iter.hasNext())
            result.append(sprint_function.call(iter.next().elem));

        while (iter.hasNext())
            result.append(", ").append(sprint_function.call(iter.next().elem));

        return result.toString();
    }

    // =========================================================================================

    public LinkedListIterator<T, LINK_TYPE> iterator() {
        return new LinkedListIterator<>(this);
    }
}
