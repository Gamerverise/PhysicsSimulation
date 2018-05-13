package edsel.cfgs.regex_cfg;

import edsel.lib.cfg_model.CFG_Symbol;
import edsel.lib.cfg_model.CFG_Terminal;

import static edsel.cfgs.regex_cfg.RegexTerminalID.*;

public class RegexTerminal
        extends CFG_Terminal<RegexTerminalID, Character>
        implements CFG_Symbol
{
    public RegexTerminal(RegexTerminalID id, Character value) {
        super(id, value);
    }

    // =========================================================================================

    public static RegexTerminal OP;
    public static RegexTerminal CP;

    public static RegexTerminal VB;
    public static RegexTerminal ST;

    public static RegexTerminal UB;

    public static RegexTerminal LITERAL;

    {
        OP = new RegexTerminal(OP_ID, '(');
        CP = new RegexTerminal(CP_ID, ')');

        VB = new RegexTerminal(VB_ID, '|');
        ST = new RegexTerminal(ST_ID, '*');

        UB = new RegexTerminal(UB_ID, '_');

        LITERAL = new RegexTerminal(LITERAL_ID, null);
    }
}
