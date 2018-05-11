package edsel.lib.cfg_parser.regex_cfg;

import edsel.lib.cfg_model.RCFG_Terminal;

public abstract
class RegexTerminal<REDUCTION_TYPE>
        extends RCFG_Terminal
        <RegexTerminal<REDUCTION_TYPE>,
                RegexProduction<REDUCTION_TYPE>,
                RegexSymbol<REDUCTION_TYPE>,
                RegexTerminalID,
                Character,
                RegexProductionID,
                REDUCTION_TYPE>
implements RegexSymbol<REDUCTION_TYPE>
{}
