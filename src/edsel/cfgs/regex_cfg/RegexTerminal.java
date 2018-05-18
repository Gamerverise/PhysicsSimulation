package edsel.cfgs.regex_cfg;

import edsel.lib.cfg_model.CFG_Terminal;
import edsel.lib.cfg_parser.parse_node.Token;

import static edsel.cfgs.regex_cfg.RegexTerminalID.*;

public class RegexTerminal
        extends CFG_Terminal<RegexTerminalID, Character>
{
    public char character;

    public RegexTerminal(RegexTerminalID id, String pattern, String name) {
        super(id, pattern, name);
        character = pattern.charAt(0);
    }

    public void reduce(Token<RegexTerminalID, Character> tok)
    {
        tok.value = tok.src_string.get_string().charAt(0);
    }

    // =========================================================================================

    public static RegexTerminal OP = new RegexTerminal(OP_ID, "(", null);
    public static RegexTerminal CP = new RegexTerminal(CP_ID, ")", null);

    public static RegexTerminal VB = new RegexTerminal(VB_ID, "|", null);
    public static RegexTerminal ST = new RegexTerminal(ST_ID, "*", null);

    public static RegexTerminal UB = new RegexTerminal(UB_ID, "_", null);

    public static RegexTerminal LITERAL = new RegexTerminal(LITERAL_ID, "[^()|*_\"]", "literal");
}
