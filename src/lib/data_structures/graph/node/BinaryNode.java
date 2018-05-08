package lib.data_structures.graph.node;

import lib.java_lang_extensions.parametrized_types.Constructor;
import lib.tokens.enums.CopyType;

import static lib.tokens.enums.CopyType.COPY_SHALLOW;

public abstract
class BinaryNode
        <T extends Constructor<T>>
        implements
        Constructor<BinaryNode<T>>
{
    T elem;
    BinaryNode<T> left;
    BinaryNode<T> right;

    public BinaryNode() {
        elem = null;
        left =  null;
        right = null;
    }

    public BinaryNode(T elem) {
        this(elem, null, null, COPY_SHALLOW);
    }

    public BinaryNode(T elem, BinaryNode<T> left, BinaryNode<T> right, CopyType copy_type) {
        this.elem = elem;
        this.left = left;
        this.right = right;
    }

    public BinaryNode(T elem, CopyType copy_type) {
        if (copy_type == COPY_SHALLOW)
            this.elem = elem;
        else
            this.elem = elem.new_copy(copy_type);

        this.next = null;
    }

    public BinaryNode(T elem, CopyType copy_type) {
        super(list, copy_type);
    }

    // =========================================================================================

    public BinaryNode<T> new_instance(Object... args) {
        return Constructor.new_instance(BinaryNode.class, args);
    }

    public BinaryNode<T> new_copy(CopyType copy_type) {

    }
}
