package edsel.cfgs.regex_cfg;

import edsel.lib.cfg_model.CFG;
import edsel.lib.cfg_model.CFG_Production;

import static edsel.cfgs.regex_cfg.RegexProduction.RegexProductions;
import static edsel.cfgs.regex_cfg.RegexProduction_LALR_1.RegexProductions_LALR_1;
import static edsel.cfgs.regex_cfg.RegexTerminal.RegexTerminals;

public class RegexCFG extends CFG<RegexProductionID, RegexTerminalID, Character>
{
    public static RegexCFG RegexCFG
            = new RegexCFG(RegexProduction.START, RegexProductions, RegexTerminals);

    public static RegexCFG RegexCFG_LALR_1
            = new RegexCFG(RegexProduction_LALR_1.START, RegexProductions_LALR_1, RegexTerminals);

    public RegexCFG(
            CFG_Production<RegexProductionID> start_production,
            CFG_Production<RegexProductionID>[] productions,
            RegexTerminal[] terminals)
    {
        super(start_production, productions, terminals);
    }
}
