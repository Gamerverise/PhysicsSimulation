package edsel.lib.cfg_model;

import java.util.LinkedList;

public class CFG_Production
        <ENUM_TERMINAL_ID extends Enum<ENUM_TERMINAL_ID>,
                TERMINAL_VALUE_TYPE,
                ENUM_PRODUCTION_ID extends Enum<ENUM_PRODUCTION_ID>>
        extends CFG_Symbol
        <ENUM_TERMINAL_ID,
                TERMINAL_VALUE_TYPE,
                ENUM_PRODUCTION_ID>
{
    public CFG_Production(
        CFG_Production<ENUM_TERMINAL_ID,
                TERMINAL_VALUE_TYPE,
                ENUM_PRODUCTION_ID>
        production)
    {

    }

    public ENUM_PRODUCTION_ID id;

    public LinkedList<
            CFG_Symbol<ENUM_TERMINAL_ID,
                    TERMINAL_VALUE_TYPE,
                    ENUM_PRODUCTION_ID>>
            rhs = new LinkedList<>();

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
