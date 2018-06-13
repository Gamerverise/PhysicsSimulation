package edsel.cfgs.regex_cfg;

import edsel.lib.cfg_model.CFG_Terminal;
import edsel.lib.cfg_parser.parse_node.Token;
import edsel.lib.io.CharBuffer;

import static edsel.cfgs.regex_cfg.RegexTerminalID.*;

public class RegexTerminal
        extends CFG_Terminal<RegexTerminalID, Character, RegexParser.RegexSymbolBuffer>
{
    public char character;

    public RegexTerminal(RegexTerminalID id, String pattern, String name) {
        super(id, pattern, name);
        character = pattern.charAt(0);
    }

    public RegexToken reduce(CharBuffer.CharBufferString str)
    {

        return new RegexToken(id, str.get_first_char(), str);
    }

    // =========================================================================================

    public static RegexTerminal OP = new RegexTerminal(OP_ID, "(", null);
    public static RegexTerminal CP = new RegexTerminal(CP_ID, ")", null);

    public static RegexTerminal VB = new RegexTerminal(VB_ID, "|", null);
    public static RegexTerminal ST = new RegexTerminal(ST_ID, "*", null);

    public static RegexTerminal UB = new RegexTerminal(UB_ID, "_", null);

    public static RegexTerminal LITERAL = new RegexTerminal(LITERAL_ID, "[^()|*_\"]", "literal");

    public static RegexTerminal EOF = new RegexTerminal(LITERAL_ID, "", "eof");
}
