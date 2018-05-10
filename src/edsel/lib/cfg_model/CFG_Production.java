package edsel.lib.cfg_model;

public class CFG_Production
        <TERMINAL_TYPE
                extends CFG_Terminal
                        <TERMINAL_TYPE,
                                PRODUCTION_TYPE,
                                SYMBOL_TYPE,
                                ENUM_TERMINAL_ID,
                                TERMINAL_VALUE_TYPE,
                                ENUM_PRODUCTION_ID>,
                PRODUCTION_TYPE
                        extends CFG_Production
                                <TERMINAL_TYPE,
                                        PRODUCTION_TYPE,
                                        SYMBOL_TYPE,
                                        ENUM_TERMINAL_ID,
                                        TERMINAL_VALUE_TYPE,
                                        ENUM_PRODUCTION_ID>,
                SYMBOL_TYPE
                        extends CFG_Symbol
                                <TERMINAL_TYPE,
                                        PRODUCTION_TYPE,
                                        SYMBOL_TYPE,
                                        ENUM_TERMINAL_ID,
                                        TERMINAL_VALUE_TYPE,
                                        ENUM_PRODUCTION_ID>,
                ENUM_TERMINAL_ID extends Enum<ENUM_TERMINAL_ID>,
                TERMINAL_VALUE_TYPE,
                ENUM_PRODUCTION_ID extends Enum<ENUM_PRODUCTION_ID>>
        implements
        CFG_Symbol
                <TERMINAL_TYPE,
                        PRODUCTION_TYPE,
                        SYMBOL_TYPE,
                        ENUM_TERMINAL_ID,
                        TERMINAL_VALUE_TYPE,
                        ENUM_PRODUCTION_ID>
{
    public ENUM_PRODUCTION_ID
            id;

    public CFG_Symbol
            <TERMINAL_TYPE,
                    PRODUCTION_TYPE,
                    SYMBOL_TYPE,
                    ENUM_TERMINAL_ID,
                    TERMINAL_VALUE_TYPE,
                    ENUM_PRODUCTION_ID>[]
            rhs;

    @SafeVarargs
    public CFG_Production(
            ENUM_PRODUCTION_ID
                    id,
            CFG_Symbol
                    <TERMINAL_TYPE,
                            PRODUCTION_TYPE,
                            SYMBOL_TYPE,
                            ENUM_TERMINAL_ID,
                            TERMINAL_VALUE_TYPE,
                            ENUM_PRODUCTION_ID>...
                    rhs)
    {
        this.id = id;
        this.rhs = rhs;
    }

//    public DoublyLinkedList
//            <CFG_Terminal
//                    <ENUM_TERMINAL_ID, TERMINAL_VALUE_TYPE, ENUM_PRODUCTION_ID>>
//            start_terminals;
//
//    public void
//    transition(Stack
//                       <CFG_Symbol
//                               <ENUM_TERMINAL_ID,
//                                       TERMINAL_VALUE_TYPE,
//                                       ENUM_PRODUCTION_ID>>
//                       state_stack,
//               CFG_Symbol
//                       <ENUM_TERMINAL_ID,
//                               TERMINAL_VALUE_TYPE,
//                               ENUM_PRODUCTION_ID>
//                       sym)
//
//    {
//
//    }
}
