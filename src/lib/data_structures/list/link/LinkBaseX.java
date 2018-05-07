package lib.data_structures.list.link;

import lib.tokens.enums.CopyType;

import static lib.tokens.enums.CopyType.COPY_SHALLOW;

public abstract
class LinkBaseX
        <T_RAW_TYPE extends ConstructableRawType,
                T_PARAMETRIZED_TYPE extends T_RAW_TYPE,
                LINK_BASE_TYPE
                        extends LinkBaseX<T_RAW_TYPE, T_PARAMETRIZED_TYPE, LINK_BASE_TYPE>>
        implements
        ConstructableRawType<T_RAW_TYPE,
                                        T_PARAMETRIZED_TYPE,
                                        LINK_BASE_TYPE>

//        ConstructableDataStructureX
//                <T_RAW_TYPE,
//                        T_PARAMETRIZED_TYPE,
//                        LinkBaseX<T_RAW_TYPE, T_PARAMETRIZED_TYPE, LINK_BASE_PARAMETRIZED_TYPE>,
//                        LINK_BASE_PARAMETRIZED_TYPE>
{
    public T_PARAMETRIZED_TYPE elem;
    public LinkBaseX<T_RAW_TYPE, T_PARAMETRIZED_TYPE> next;

    public LinkBaseX() {
        elem = null;
        next = null;
    }

    public LinkBaseX(T_PARAMETRIZED_TYPE elem, LinkBaseX<T_RAW_TYPE, T_PARAMETRIZED_TYPE> next) {
        this.elem = elem;
        this.next = next;
    }

    public LinkBaseX(T_PARAMETRIZED_TYPE elem, CopyType copy_type) {
        if (copy_type == COPY_SHALLOW)
            this.elem = elem;
        else
            this.elem = elem.new_copy(copy_type);

        this.next = null;
    }
}
