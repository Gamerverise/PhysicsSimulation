package lib.data_structures.list;

import lib.java_lang_extensions.parametrized_types.ConstructableParametrizedType;
import lib.data_structures.list.link.DoubleLink;
import lib.tokens.enums.CopyType;

import java.util.Iterator;

public class DoublyLinkedList<T extends ConstructableParametrizedType<T>>
    extends LinkedList<D>
    implements Iterable<DoubleLink<T>>
{
    public static class LinkedListIterator<T extends ConstructableParametrizedType<T>>
        implements Iterator<DoubleLink<T>>
    {
        public DoubleLink<T> cur;

        public LinkedListIterator(DoublyLinkedList<T> list) {
            cur = list.head;
        }

        public boolean hasNext() {
            return cur.next != null;
        }

        public DoubleLink<T> next() {
            cur = cur.next;
            return cur;
        }
    }

    public DoubleLink<T> head;
    public DoubleLink<T> tail;
    public int length;

    public DoublyLinkedList() {
        super();
        head = null;
        tail = null;
        length = 0;
    }

    public DoublyLinkedList(DoubleLink<T> link) {
        head = link;
        tail = link;
        int length = 0;

        while(tail != null) {
            tail = tail.next;
            length++;
        }
    }

    public DoublyLinkedList(DoublyLinkedList<T> list, CopyType copy_type) {
        this();

        for(DoubleLink<T> link : list)
            append(link.elem.new_copy(copy_type));
    }

    public void append(T elem) {
        tail.next = new DoubleLink<>(elem, tail, null);
        length += 1;
    }

    public void append(DoubleLink<T> link) {
        link.prev = tail;
        link.next = null;
        tail.next = link;
        length += 1;
    }

    public DoublyLinkedList<T> split(int n) {
        if (length == 0 || n == 0 || n >= length)
            return null;

        DoubleLink<T> new_tail = head;

        int i;
        for (i = 1; i < n; i++)
            new_tail = new_tail.next;

        DoublyLinkedList<T> list = new DoublyLinkedList<>(tail.next);
        new_tail.next = null;
        length = i;

        return list;
    }

    public DoublyLinkedList<T> new_copy(CopyType copy_type) {
        return new DoublyLinkedList<>(this, copy_type);
    }

    public Iterator<DoubleLink<T>> iterator() {
        return new LinkedListIterator<>(this);
    }
}
