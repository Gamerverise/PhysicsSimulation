package lib.data_structures.list.link;

import lib.java_lang_extensions.parametrized_class.Constructable;
import lib.tokens.enums.CopyType;

import static lib.tokens.enums.CopyType.COPY_SHALLOW;

public abstract
class LinkBase
        <T extends CONSTRUCTABLE_TYPE,
                CONSTRUCTABLE_TYPE extends Constructable<T, CONSTRUCTABLE_TYPE>,
                LINK_TYPE extends LinkBase<T, CONSTRUCTABLE_TYPE, LINK_TYPE>>
        implements
        Constructable<LINK_TYPE, LinkBase<T, CONSTRUCTABLE_TYPE, LINK_TYPE>>
{
    public T elem;
    public LINK_TYPE next;

    public LinkBase() {
        elem = null;
        next = null;
    }

    public LinkBase(T elem, LINK_TYPE next) {
        this.elem = elem;
        this.next = next;
    }

    public LinkBase(T elem, CopyType copy_type) {
        if (copy_type == COPY_SHALLOW)
            this.elem = elem;
        else
            this.elem = elem.new_copy(copy_type);

        this.next = null;
    }
}

