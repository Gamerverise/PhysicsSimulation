package lib.data_structures.list;

import lib.data_structures.list.link.SingleLink;
import lib.java_lang_extensions.parametrized_types.Instantiator;
import lib.tokens.enums.CopyType;

public class LinkedList<T extends Instantiator<T>>
    extends LinkedListQualifiedBase<T, SingleLink<T>, LinkedList<T>>
{
    public LinkedList() {
        super();
    }

    @SafeVarargs
    public LinkedList(T... elems) {
        super(elems);
    }

    public LinkedList(LinkedList<T> list, CopyType copy_type) {
        super(list, copy_type);
    }

    // =========================================================================================

    public LinkedList<T> self() {
        return this;
    }

    public LinkedList<T> new_instance(Object... args) {
        return Instantiator.new_instance(LinkedList.class, args);
    }

    // =========================================================================================

    public LinkedListAbstract<T, SingleLink<T>> new_copy_abstract(CopyType copy_type){
        return new LinkedList<>(this, copy_type);
    }

    public SingleLink<T> new_link(T elem) {
        return new SingleLink<>(elem);
    }
}
