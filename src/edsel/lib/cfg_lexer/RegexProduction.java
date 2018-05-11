package edsel.lib.cfg_lexer;

import edsel.lib.cfg_model.RCFG_Production;
import edsel.lib.cfg_parser.regex_cfg.RegexProductionID;
import edsel.lib.cfg_parser.regex_cfg.RegexSymbol;
import edsel.lib.cfg_parser.regex_cfg.RegexTerminal;
import edsel.lib.cfg_parser.regex_cfg.RegexTerminalID;

public abstract
class RegexProduction<REDUCTION_TYPE>
        extends
        RCFG_Production
                <RegexTerminal<REDUCTION_TYPE>,
                        RegexProduction<REDUCTION_TYPE>,
                        RegexSymbol<REDUCTION_TYPE>,
                        RegexTerminalID,
                        Character,
                        RegexProductionID,
                        REDUCTION_TYPE>
implements RegexSymbol<REDUCTION_TYPE>
{
    @SafeVarargs
    public RegexProduction(
            RegexProductionID                   id,
            RegexSymbol<REDUCTION_TYPE>...      rhs)
    {
        super(id, rhs);
    }
}
