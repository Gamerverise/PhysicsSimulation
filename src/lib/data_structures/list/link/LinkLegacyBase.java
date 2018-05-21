package lib.data_structures.list.link;

import lib.java_lang_extensions.parametrized_types.Copyable;
import lib.tokens.enums.CopyType;

public class LinkLegacyBase<T, LINK_TYPE extends LinkLegacyBase<T, LINK_TYPE>>
        extends Copyable<LINK_TYPE>
{
    public T elem;
    public LINK_TYPE next;

    public LinkLegacyBase() {
        elem = null;
        next = null;
    }

    public LinkLegacyBase(T elem) {
        this.elem = elem;
        this.next = null;
    }

    public LinkLegacyBase(T elem, LINK_TYPE next) {
        this.elem = elem;
        this.next = next;
    }

    public LinkLegacyBase(LinkLegacyBase<T, LINK_TYPE> link, CopyType copy_type) {
        elem = link.elem;
        next = link.next;
    }
}
