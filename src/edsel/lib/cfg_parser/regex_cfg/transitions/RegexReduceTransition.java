package edsel.lib.cfg_parser.regex_cfg.transitions;


import edsel.lib.cfg_parser.regex_cfg.*;
import edsel.lib.cfg_parser.transitions.AbstractReduceTransition;

public class RegexReduceTransition<REDUCTION_TYPE>
        extends
        AbstractReduceTransition
                <RegexTerminal
                        <REDUCTION_TYPE>,
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
    public RegexReduceTransition(
            RegexTerminal<REDUCTION_TYPE> terminal,
            RegexStateStackEntry<REDUCTION_TYPE> new_state,
            RegexProduction<REDUCTION_TYPE> production)
    {
        super(terminal, new_state, production);
    }
}
