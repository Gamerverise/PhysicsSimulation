package edsel.lib.cfg_model;

import java.util.LinkedList;
import java.util.Stack;

public class CFG_Production
        <ENUM_TERMINAL_ID extends Enum<ENUM_TERMINAL_ID>,
                TERMINAL_VALUE_TYPE,
                ENUM_PRODUCTION_ID extends Enum<ENUM_PRODUCTION_ID>>
    extends CFG_Symbol<ENUM_TERMINAL_ID, TERMINAL_VALUE_TYPE, ENUM_PRODUCTION_ID> {

    public enum TransitionType {
        SHIFT,
        REDUCE,
        SHIFT_OR_REDUCE,
        NOT_APPLICABLE
    }

    public ENUM_PRODUCTION_ID id;

    public LinkedList<
            CFG_Symbol<ENUM_TERMINAL_ID,
                    TERMINAL_VALUE_TYPE,
                    ENUM_PRODUCTION_ID>>
            rhs;

    public LinkedList
            <CFG_Terminal
                    <ENUM_TERMINAL_ID, TERMINAL_VALUE_TYPE, ENUM_PRODUCTION_ID>>
            start_terminals;

    public void
    transition(Stack
                       <CFG_Symbol
                               <ENUM_TERMINAL_ID,
                                       TERMINAL_VALUE_TYPE,
                                       ENUM_PRODUCTION_ID>>
                       state_stack,
               CFG_Symbol
                       <ENUM_TERMINAL_ID,
                               TERMINAL_VALUE_TYPE,
                               ENUM_PRODUCTION_ID>
                       sym)

    {

    }
}
