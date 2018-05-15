package lib.data_structures.list;

import lib.data_structures.list.link.DoubleLink;
import lib.java_lang_extensions.parametrized_types.Instantiator;
import lib.tokens.enums.CopyType;

public class DoublyLinkedList<T extends Instantiator<T>>
        extends LinkedListQualifiedBase<T, DoubleLink<T>, DoublyLinkedList<T>>
{
    public DoublyLinkedList() {
        super();
    }

    @SafeVarargs
    public DoublyLinkedList(T... elems) {
        super(elems);
    }

    public DoublyLinkedList(DoublyLinkedList<T> list, CopyType copy_type) {
        super(list, copy_type);
    }

    // =========================================================================================

    public DoublyLinkedList<T> new_instance(Object... args) {
        return Instantiator.new_instance(DoublyLinkedList.class, args);
    }

    public LinkedListAbstract<T, DoubleLink<T>> new_copy_abstract(CopyType copy_type){
        return new DoublyLinkedList<>(this, copy_type);
    }

    public DoubleLink<T> new_link(T elem) {
        return new DoubleLink<>(elem);
    }

    // =========================================================================================

    public void append(DoubleLink<T> link) {
        link.prev = tail;
        super.append(link);
    }

    // =========================================================================================

    public DoublyLinkedList<T> self() {
        return this;
    }

    public DoublyLinkedList<T> split(int n) {
        DoublyLinkedList<T> end_part = super.split(n);

        if (end_part.length == 0 || end_part == this)
            return end_part;

        end_part.head.prev = null;

        return end_part;
    }
}
