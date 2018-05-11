package edsel.lib.cfg_parser.regex_cfg;

import edsel.lib.cfg_model.CFG;
import edsel.lib.cfg_model.CFG_Production;
import edsel.lib.cfg_model.CFG_Symbol;
import edsel.lib.cfg_model.CFG_Terminal;

import java.util.LinkedList;

public class RegexCFG<REDUCTION_TYPE>
    extends
        CFG
                <RegexTerminal<REDUCTION_TYPE>,
                        RegexProduction<REDUCTION_TYPE>,
                        RegexSymbol<REDUCTION_TYPE>,
                        RegexTerminalID,
                        Character,
                        RegexProductionID>
{
    public static class InvalidCFG_Input extends Exception {}

    // =========================================================================================

    public LinkedList<RegexTerminal<REDUCTION_TYPE>> terminals;

    public RegexTerminal<REDUCTION_TYPE> eof;

    public LinkedList<RegexProduction<REDUCTION_TYPE>> productions;

    public RegexProduction<REDUCTION_TYPE> root_production;

    // =========================================================================================

    public RegexCFG(
            LinkedList<RegexTerminal<REDUCTION_TYPE>> terminals,
            RegexTerminal<REDUCTION_TYPE> eof,
            LinkedList<RegexProduction<REDUCTION_TYPE>> productions,
            RegexProduction<REDUCTION_TYPE> root_production)
    {
        super(terminals, eof, productions, root_production);
    }
}
