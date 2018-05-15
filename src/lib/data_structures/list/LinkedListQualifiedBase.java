package lib.data_structures.list;

import lib.data_structures.list.link.LinkBase;
import lib.java_lang_extensions.parametrized_types.Instantiator;
import lib.tokens.enums.CopyType;

public abstract
class LinkedListQualifiedBase
        <T extends Instantiator<T>,
                LINK_TYPE extends LinkBase<T, LINK_TYPE>,
                LIST_TYPE extends LinkedListQualifiedBase<T, LINK_TYPE, LIST_TYPE>>
        extends
        LinkedListQualified<T, LINK_TYPE, LIST_TYPE>
{
    public LinkedListQualifiedBase() {
        super();
    }

    @SafeVarargs
    public LinkedListQualifiedBase(T... elems) {
        super(elems);
    }

    public LinkedListQualifiedBase(LIST_TYPE list, CopyType copy_type) {
        super(list, copy_type);
    }

    // =========================================================================================

    public LIST_TYPE new_copy() {
        return new_instance();
    }

    public LIST_TYPE new_copy(CopyType copy_type) {
        return new_instance(copy_type);
    }
}
