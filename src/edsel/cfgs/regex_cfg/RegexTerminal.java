package edsel.cfgs.regex_cfg;

import edsel.lib.cfg_model.CFG_Symbol;
import edsel.lib.cfg_model.CFG_Terminal;

import static edsel.cfgs.regex_cfg.RegexTerminalID.*;

public class RegexTerminal
        extends CFG_Terminal<RegexTerminalID>
        implements CFG_Symbol
{
    public RegexTerminal(RegexTerminalID id, String pattern) {
        super(id, pattern);
    }

    // =========================================================================================

    public static RegexTerminal OP = new RegexTerminal(OP_ID, "(");
    public static RegexTerminal CP = new RegexTerminal(CP_ID, ")");

    public static RegexTerminal VB = new RegexTerminal(VB_ID, "|");
    public static RegexTerminal ST = new RegexTerminal(ST_ID, "*");

    public static RegexTerminal UB = new RegexTerminal(UB_ID, "_");

    public static RegexTerminal LITERAL = new RegexTerminal(LITERAL_ID, "[^()|*_\"]");
}
