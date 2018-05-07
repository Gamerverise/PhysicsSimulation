package lib.data_structures.graph.node;

import lib.java_lang_extensions.parametrized_types.ConstructableParametrizedType;
import lib.tokens.enums.CopyType;

import static lib.tokens.enums.CopyType.COPY_SHALLOW;

public class MultiNode<T extends ConstructableParametrizedType<T>>
    extends BinaryNode<T>
{
    public MultiNode<T> prev;
    public MultiNode<T> next;

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

    public MultiNode<T> new_copy(CopyType copy_type) {
        return new MultiNode<>(this.elem, copy_type);
    }
}

