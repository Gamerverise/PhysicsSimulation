package lib.data_structures.graph.node;

import lib.data_structures.list.LinkedListLegacy;
import lib.data_structures.list.link.LinkLegacy;
import lib.java_lang_extensions.parametrized_types.Instantiator;
import lib.tokens.enums.CopyType;

import static lib.tokens.enums.CopyType.COPY_SHALLOW;

public abstract class MultiNode
        <T extends Instantiator<T>,
                LINK_TYPE extends LinkLegacy<T, LINK_TYPE>>
        extends
        InstantiatorBase<MultiNode<T, LINK_TYPE>>
{
    public T elem;
    public LinkedListLegacy<T, LINK_TYPE> children;

    public MultiNode(T elem, LinkedListLegacy<T, LINK_TYPE> children) {
        this.elem = elem;
        this.children = children;
    }

    public MultiNode(MultiNode<T, LINK_TYPE> mn, CopyType copy_type) {
        elem = mn.elem.new_copy(copy_type);
        children = mn.children.new_copy_abstract(COPY_SHALLOW);
    }

    // =========================================================================================

    public MultiNode<T, LINK_TYPE> self() {
        return this;
    }

    public MultiNode<T, LINK_TYPE> new_instance(Object... args) {
        return Instantiator.new_instance(MultiNode.class, args);
    }
}
