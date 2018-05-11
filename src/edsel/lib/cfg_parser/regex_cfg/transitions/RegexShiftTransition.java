package edsel.lib.cfg_parser.regex_cfg.transitions;

import edsel.lib.cfg_parser.regex_cfg.*;
import edsel.lib.cfg_parser.transitions.AbstractShiftTransition;

public class RegexShiftTransition<REDUCTION_TYPE>
        extends
        AbstractShiftTransition
                <RegexTerminal<REDUCTION_TYPE>,
                        RegexProduction<REDUCTION_TYPE>,
                        RegexSymbol<REDUCTION_TYPE>,
                        RegexTerminalID,
                        Character,
                        RegexProductionID,
                        REDUCTION_TYPE,
                        RegexStateStackEntry<REDUCTION_TYPE>>
        implements
        RegexStateTransition
{
    public RegexShiftTransition(
            RegexTerminal<REDUCTION_TYPE> terminal,
            RegexStateStackEntry<REDUCTION_TYPE> new_state)
    {
        super(terminal, new_state);
    }
}
