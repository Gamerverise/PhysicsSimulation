package edsel.cfgs.regex_cfg;

import edsel.lib.cfg_model.RCFG_Terminal;

import static edsel.cfgs.regex_cfg.RegexTerminalID.*;

public class RegexTerminal
        extends RCFG_Terminal<RegexTerminalID, Character>
{
    public char character;

    public RegexTerminal(RegexTerminalID id, String pattern) {
        super(id, pattern);
        character = pattern.charAt(0);
    }

    public void reduce(Token<RegexTerminalID, Character> tok)
    {
        tok.value = tok.src_string.get_string().charAt(0);
    }

    // =========================================================================================

    public static RegexTerminal OP = new RegexTerminal(OP_ID, "(");
    public static RegexTerminal CP = new RegexTerminal(CP_ID, ")");

    public static RegexTerminal VB = new RegexTerminal(VB_ID, "|");
    public static RegexTerminal ST = new RegexTerminal(ST_ID, "*");

    public static RegexTerminal UB = new RegexTerminal(UB_ID, "_");

    public static RegexTerminal LITERAL = new RegexTerminal(LITERAL_ID, "[^()|*_\"]");

    public static RegexTerminal RESTRICT = new RegexTerminal(RESTRICT_ID, "(*");
    public static RegexTerminal UNRESTRICT = new RegexTerminal(UNRESTRICT_ID, "*)");

    public static RegexTerminal GATE = new RegexTerminal(GATE_ID, "#");
}
