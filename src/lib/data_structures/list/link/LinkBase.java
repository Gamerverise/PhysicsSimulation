package lib.data_structures.list.link;

import lib.java_lang_extensions.parametrized_types.Constructor;
import lib.java_lang_extensions.parametrized_types.ConstructorBase;
import lib.tokens.enums.CopyType;

public abstract
class LinkBase
        <T extends Constructor<T>,
                LINK_TYPE extends LinkBase<T, LINK_TYPE>>
        extends
        ConstructorBase<LINK_TYPE>
{
    public T elem;
    public LINK_TYPE next;

    public LinkBase() {
        elem = null;
        next = null;
    }

    public LinkBase(T elem) {
        this.elem = elem;
        this.next = null;
    }

    public LinkBase(T elem, LINK_TYPE next) {
        this.elem = elem;
        this.next = next;
    }

    public LinkBase(LinkBase<T, LINK_TYPE> link, CopyType copy_type) {
        elem = link.elem.new_copy(copy_type);
        next = link.next.new_copy(copy_type);
    }
}
