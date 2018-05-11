package edsel.lib.cfg_model;

public class
CFG_Production
        <ENUM_PRODUCTION_ID extends Enum<ENUM_PRODUCTION_ID>>
        implements
        CFG_Symbol
{
    public ENUM_PRODUCTION_ID id;

    public CFG_Symbol[][] rhs;

    public CFG_Production(ENUM_PRODUCTION_ID id, CFG_Symbol[]... rhs) {
        this.id = id;
        this.rhs = rhs;
    }
}

// =========================================================================================

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
