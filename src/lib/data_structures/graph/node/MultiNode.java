package lib.data_structures.graph.node;

import lib.data_structures.list.LinkedList;
import lib.java_lang_extensions.parametrized_types.Copyable;
import lib.text_io.Sprintable;
import lib.tokens.enums.CopyType;

import static lib.tokens.enums.CopyType.COPY_SHALLOW;

public abstract class MultiNode<T extends Copyable<T>>
        extends Copyable<MultiNode<T>>
        implements
        Sprintable
{
    public T elem;
    public LinkedList<MultiNode<T>> children;

    public MultiNode(T elem, LinkedList<MultiNode<T>> children) {
        this.elem = elem;
        this.children = children;
    }

    public MultiNode(MultiNode<T> mn, CopyType copy_type) {
        elem = mn.elem.new_copy(copy_type);
        children = mn.children.new_copy(COPY_SHALLOW);
    }
}
