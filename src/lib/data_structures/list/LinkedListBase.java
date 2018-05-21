package lib.data_structures.list;

import lib.data_structures.list.link.LinkLegacyBase;
import lib.java_lang_extensions.parametrized_types.Copyable;
import lib.tokens.enums.CopyType;

public abstract
class LinkedListBase
        <T extends Copyable<T>,
                LINK_TYPE extends LinkLegacyBase<T, LINK_TYPE>,
                LIST_TYPE extends LinkedListBase<T, LINK_TYPE, LIST_TYPE>>
        extends
        LinkedListLegacyBase<T, LINK_TYPE, LIST_TYPE>
{
    public LinkedListBase() {
        super();
    }

    @SafeVarargs
    public LinkedListBase(T... elems) {
        super(elems);
    }

    public LinkedListBase(LIST_TYPE list, CopyType copy_type) {
        super(list, copy_type);
    }
}
