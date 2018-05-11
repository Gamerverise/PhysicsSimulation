package edsel.lib.cfg_parser.regex_cfg.transitions;


import edsel.lib.cfg_parser.regex_cfg.*;
import edsel.lib.cfg_parser.transitions.AbstractStateStackEntry;

public class RegexStateStackEntry<REDUCTION_TYPE>
        extends
        AbstractStateStackEntry
                <RegexTerminal<REDUCTION_TYPE>,
                        RegexProduction<REDUCTION_TYPE>,
                        RegexSymbol<REDUCTION_TYPE>,
                        RegexTerminalID,
                        Character,
                        RegexProductionID,
                        REDUCTION_TYPE,
                        RegexStateStackEntry<REDUCTION_TYPE>>
{}
