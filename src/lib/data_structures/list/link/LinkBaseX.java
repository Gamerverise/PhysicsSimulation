package lib.data_structures.list.link;

import lib.java_lang_extensions.parametrized_types.ConstructableX;
import lib.tokens.enums.CopyType;

import static lib.tokens.enums.CopyType.COPY_SHALLOW;

public abstract
class LinkBaseX
        <T_RAW_TYPE,
                T_PARAMETRIZED_TYPE
                    extends ConstructableX
                <T_RAW_TYPE, T_PARAMETRIZED_TYPE>,
                RAW_LINK_TYPE
                        extends ConstructableX
<T,
                                RAW_LINK_TYPE,
                                PARAMETRIZED_LINK_TYPE>,
                PARAMETRIZED_LINK_TYPE
                        extends RAW_LINK_TYPE>
        implements
        ConstructableX<T, RAW_LINK_TYPE, PARAMETRIZED_LINK_TYPE>
{
    public T elem;
    public LINK_TYPE next;

    public LinkBaseX() {
        elem = null;
        next = null;
    }

    public LinkBaseX(T elem, LINK_TYPE next) {
        this.elem = elem;
        this.next = next;
    }

    public LinkBaseX(T elem, CopyType copy_type) {
        if (copy_type == COPY_SHALLOW)
            this.elem = elem;
        else
            this.elem = elem.new_copy(copy_type);

        this.next = null;
    }
}
