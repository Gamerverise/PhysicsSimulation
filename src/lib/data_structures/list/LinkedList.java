package lib.data_structures.list;

import lib.data_structures.list.link.SingleLink;
import lib.java_lang_extensions.parametrized_types.ConstructableBase;
import lib.tokens.enums.CopyType;

import java.util.Iterator;

import static lib.tokens.enums.CopyType.COPY_SHALLOW;

public class LinkedList<T extends ConstructableBase<T>>
    implements Iterable<SingleLink<T>>
{
    public static class LinkedListIterator<T extends ConstructableBase<T>>
        implements Iterator<SingleLink<T>>
    {
        public SingleLink<T> cur;

        public LinkedListIterator(LinkedList<T> list) {
            cur = list.head;
        }

        public boolean hasNext() {
            return cur.next != null;
        }

        public SingleLink<T> next() {
            cur = cur.next;
            return cur;
        }
    }

    public SingleLink<T> head;
    public SingleLink<T> tail;
    public int length;

    public LinkedList() {
        super();
        head = null;
        tail = null;
        length = 0;
    }

    public LinkedList(SingleLink<T> link) {
        head = link;
        tail = link;
        int length = 0;

        while(tail != null) {
            tail = tail.next;
            length++;
        }
    }

    public LinkedList(LinkedList<T> list, CopyType copy_type) {
        this();

        for(SingleLink<T> link : list)
            append(link.elem.new_copy(copy_type));
    }

    public void append(T elem) {
        tail.next = new SingleLink<>(elem, COPY_SHALLOW);
        length += 1;
    }

    public void append(SingleLink<T> link) {
        link.next = null;
        tail.next = link;
        length += 1;
    }

    public LinkedList<T> split(int n) {
        if (length == 0 || n == 0 || n >= length)
            return null;

        SingleLink<T> new_tail = head;

        int i;
        for (i = 1; i < n; i++)
            new_tail = new_tail.next;

        LinkedList<T> list = new LinkedList<>(tail.next);
        new_tail.next = null;
        length = i;

        return list;
    }

    public LinkedList<T> new_copy(CopyType copy_type) {
        return new LinkedList<>(this, copy_type);
    }

    public Iterator<SingleLink<T>> iterator() {
        return new LinkedListIterator<>(this);
    }
}
