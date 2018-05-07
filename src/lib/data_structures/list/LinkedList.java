package lib.data_structures.list;

import lib.data_structures.list.link.SingleLink;
import lib.tokens.enums.CopyType;

public class LinkedList<T extends Constructable<T>>
    extends LinkedListBase<T, SingleLink<T>, LinkedList<T>>
{
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

    public LinkedList<T> new_instance(Object... args) {
//        return ConstructableRawType.class.new_instance(this.getClass(), args);
        return Constructable.class.new_instance((Class<LinkedList>) null, args);
    }

    public LinkedList<T> new_copy(CopyType copy_type) {
        return new LinkedList<>(this, copy_type);
    }
}
