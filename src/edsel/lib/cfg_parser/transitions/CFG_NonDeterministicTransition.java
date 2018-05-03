package edsel.lib.cfg_parser.transitions;

import edsel.lib.cfg_model.CFG_Production;
import edsel.lib.cfg_model.CFG_Terminal;

import java.util.List;

public class CFG_NonDeterministicTransition
        <ENUM_TERMINAL_ID extends Enum<ENUM_TERMINAL_ID>,
                TERMINAL_VALUE_TYPE,
                ENUM_PRODUCTION_ID extends Enum<ENUM_PRODUCTION_ID>,
                REDUCTION_TYPE>
{
    CFG_Production<ENUM_TERMINAL_ID,
                        TERMINAL_VALUE_TYPE,
                        ENUM_PRODUCTION_ID,
                        REDUCTION_TYPE>
            production_lhs;

    CFG_Terminal<ENUM_TERMINAL_ID,
                TERMINAL_VALUE_TYPE,
                ENUM_PRODUCTION_ID,
                REDUCTION_TYPE>
            input;

    List<CFG_Production<ENUM_TERMINAL_ID,
                        TERMINAL_VALUE_TYPE,
                        ENUM_PRODUCTION_ID,
                        REDUCTION_TYPE>>
            production_rhs;
}
