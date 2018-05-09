package lib.data_structures.graph.node;

import lib.java_lang_extensions.parametrized_types.Constructor;
import lib.java_lang_extensions.parametrized_types.ConstructorBase;
import lib.tokens.enums.CopyType;

public class
BinaryNode <T extends Constructor<T>>
        extends
        ConstructorBase<BinaryNode<T>>
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
        this(elem, null, null);
    }

    public BinaryNode(T elem, BinaryNode<T> left, BinaryNode<T> right) {
        this.elem = elem;
        this.left = left;
        this.right = right;
    }

    public BinaryNode(BinaryNode<T> node, CopyType copy_type) {
        elem = node.elem.new_copy(copy_type);
        left = node.left.new_copy(copy_type);
        right = node.right.new_copy(copy_type);
    }

    // =========================================================================================

    public BinaryNode<T> self() {
        return this;
    }

    public BinaryNode<T> new_instance(Object... args) {
        return Constructor.new_instance(BinaryNode.class, args);
    }
}
