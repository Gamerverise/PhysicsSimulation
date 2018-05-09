package lib.data_structures.graph.node;

import lib.data_structures.list.DoublyLinkedList;
import lib.java_lang_extensions.parametrized_types.Constructor;
import lib.java_lang_extensions.parametrized_types.ConstructorBaseDataStructure;
import lib.java_lang_extensions.parametrized_types.ConstructorDataStructure;
import lib.java_lang_extensions.parametrized_types.ConstructorShallowDataStructure;
import lib.tokens.enums.CopyType;

import static lib.tokens.enums.CopyType.COPY_SHALLOW;

public class MultiNode
        <T extends Constructor<T>,
                LIST_TYPE extends ConstructorShallowDataStructure<T, LIST_TYPE>>
        extends
        ConstructorBaseDataStructure<T, MultiNode<T, LIST_TYPE>>
{
    public T elem;
    public DoublyLinkedList<MultiNode<T>> nodes;

    public MultiNode(T elem, MultiNode<T> prev, MultiNode<T> next) {
        this.elem = elem;
        this.prev = prev;
        this.next = next;
    }

    public MultiNode(T elem, CopyType copy_type) {
        if (copy_type == COPY_SHALLOW)
            this.elem = elem;
        else
            this.elem = elem.new_copy(copy_type);
        
        this.prev = null;
        this.next = null;
    }

    // =========================================================================================

    public MultiNode<T> self() {
        return this;
    }

    public MultiNode<T> new_instance(Object... args) {
        return Constructor.new_instance(BinaryNode.class, args);
    }
}

