package edsel.cfgs.regex_cfg;

import edsel.lib.cfg_model.CFG;

import java.util.LinkedList;

public class RegexCFG
    extends
        CFG
                <RegexTerminal,
                        RegexProduction,
                        RegexTerminalID,
                        Character,
                        RegexProductionID>
{
    public RegexCFG(
            LinkedList<RegexTerminal> terminals,
            RegexTerminal eof,
            LinkedList<RegexProduction> productions,
            RegexProduction root_production)
    {
        super(terminals, eof, productions, root_production);
    }
}
