package edsel.cfgs.regex_cfg;

import edsel.lib.cfg_parser.RCFG_Parser;

import static edsel.cfgs.regex_cfg.RegexProduction.*;
import static edsel.cfgs.regex_cfg.RegexProduction.OR;
import static edsel.cfgs.regex_cfg.RegexProduction.REPEAT;

public class RegexRCFG extends RCFG_Parser<RegexProductionID, RegexProduction>
{
    public RegexRCFG()
    {
        super(START, SUB_EXPR, GROUP, AND, OR, REPEAT);
    }

    public RegexProductionID get_production(
            SymbolBuffer.SymbolBufferString
                    production_name)
    {
        for(RegexProduction production : productions)
            if (production_name.get_string() == production.name)
                return production.id;

        return null;
    }

    public static RegexRCFG RegexRCFG= new RegexRCFG();
}
