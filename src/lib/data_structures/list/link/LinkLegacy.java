package lib.data_structures.list.link;

import lib.java_lang_extensions.parametrized_types.Copyable;
import lib.tokens.enums.CopyType;

public class LinkLegacy<T>
        extends LinkLegacyBase<T, LinkLegacy<T>>
{
    public LinkLegacy() {
        elem = null;
        next = null;
    }

    public LinkLegacy(T elem) {
        this.elem = elem;
        this.next = null;
    }

    public LinkLegacy(T elem, LinkLegacy<T> next) {
        this.elem = elem;
        this.next = next;
    }

    public LinkLegacy(LinkLegacy<T> link, CopyType copy_type) {
        elem = link.elem;
        next = link.next;
    }
}
