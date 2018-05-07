package lib.data_structures.list.link;

import lib.java_lang_extensions.parametrized_types.ConstructableDataStructure;
import lib.tokens.enums.CopyType;

public class SingleLinkCDS
        <T extends Constructable<T>>
        implements
        ConstructableDataStructure<T, SingleLinkCDS<T>>
{
    public SingleLinkCDS() {}

    public SingleLinkCDS(T elem, SingleLinkCDS<T> next) {
        super(elem, next);
    }

    public SingleLinkCDS(T elem, CopyType copy_type) {
        super(elem, copy_type);
    }

    // =========================================================================================

    public SingleLinkCDS<T> new_instance(Object... args) {
        return null;
    }

    public SingleLinkCDS<T> new_instance(SingleLinkCDS<T> link, CopyType copy_type) {
        return new SingleLinkCDS<>(link.elem, copy_type);
    }

    public SingleLinkCDS<T> new_copy(CopyType copy_type) {
        return new SingleLinkCDS<>(this.elem, copy_type);
    }
}
