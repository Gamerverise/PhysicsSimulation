package lib.data_structures.list;

import lib.data_structures.list.link.LinkLegacy;
import lib.data_structures.list.link.LinkLegacyBase;
import lib.tokens.enums.CopyType;

public class LinkedListLegacy<T>
        extends LinkedListLegacyBase<T, LinkLegacy<T>, LinkedListLegacy<T>>
{
    public LinkedListLegacy() {
        super();
    }

    @SafeVarargs
    public LinkedListLegacy(T... elems) {
        super(elems);
    }

    public LinkedListLegacy(LinkedListLegacy<T> list, CopyType copy_type) {
        super(list, copy_type);
    }

    // =========================================================================================

    public LinkedListLegacy<T> self() {
        return this;
    }

    public LinkLegacy<T> new_link(T elem) {
        return new LinkLegacy<>(elem);
    }
}
