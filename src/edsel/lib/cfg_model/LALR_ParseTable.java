package edsel.lib.cfg_model;

import java.util.Stack;

public class
LALR_ParseTable
        <ENUM_TERMINAL_ID extends Enum<ENUM_TERMINAL_ID>,
                TERMINAL_VALUE_TYPE,
                ENUM_PRODUCTION_ID extends Enum<ENUM_PRODUCTION_ID>>
{
    public enum ParseTransitionAction {
        SHIFT,
        REDUCE
    }

    public class ParseTableEntry {}

    public class SHIFT_TypeParseTableEntry extends ParseTableEntry {
        int next_state;
    }

    public class REDUCE_TypeParseTableEntry extends ParseTableEntry {
        int next_state;
        CFG_Production
                <ENUM_TERMINAL_ID,
                        TERMINAL_VALUE_TYPE,
                        ENUM_PRODUCTION_ID>
                matched_production;
    }

    public class StateStackEntry {
        int                                         state;
        CFG_Symbol
                <ENUM_TERMINAL_ID,
                        TERMINAL_VALUE_TYPE,
                        ENUM_PRODUCTION_ID>         symbol;
    }

    public ParseTableEntry get_transition(
            Stack<
                    CFG_Symbol
                            <ENUM_TERMINAL_ID,
                                    TERMINAL_VALUE_TYPE,
                                    ENUM_PRODUCTION_ID>>        state_stack,
            CFG_Terminal
                    <ENUM_TERMINAL_ID,
                            TERMINAL_VALUE_TYPE,
                            ENUM_PRODUCTION_ID>                 terminal)
    {
        return new SHIFT_TypeParseTableEntry();
    }
}
