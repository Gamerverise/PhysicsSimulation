package edsel.lib.cfg_parser.transitions.old;

import edsel.lib.cfg_model.CFG_Production;
import edsel.lib.cfg_model.CFG_Symbol;
import edsel.lib.cfg_model.CFG_Terminal;

import java.util.Stack;

public class CFG_DeterministicTransition
        <ENUM_TERMINAL_ID extends Enum<ENUM_TERMINAL_ID>,
                TERMINAL_VALUE_TYPE,
                ENUM_PRODUCTION_ID extends Enum<ENUM_PRODUCTION_ID>>
{
    public CFG_Production<ENUM_TERMINAL_ID,
                        TERMINAL_VALUE_TYPE,
                        ENUM_PRODUCTION_ID>
            production_lhs;

    public CFG_Terminal<ENUM_TERMINAL_ID,
                        TERMINAL_VALUE_TYPE,
                        ENUM_PRODUCTION_ID>
            input;

    public CFG_Production<ENUM_TERMINAL_ID,
                        TERMINAL_VALUE_TYPE,
                        ENUM_PRODUCTION_ID>
            production_rhs;

    public TransitionType
    get_transition(Stack
                           <CFG_Symbol
                                   <ENUM_TERMINAL_ID,
                                           TERMINAL_VALUE_TYPE,
                                           ENUM_PRODUCTION_ID>>
                           state_stack,
                   CFG_Symbol
                           <ENUM_TERMINAL_ID,
                                   TERMINAL_VALUE_TYPE,
                                   ENUM_PRODUCTION_ID>
                           symbol)
    {

    }
}
