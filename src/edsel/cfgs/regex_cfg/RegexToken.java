package edsel.cfgs.regex_cfg;

import edsel.lib.cfg_parser.parse_node.Token;

public class RegexToken extends Token<RegexTerminalID, Character> {

    public RegexToken(RegexTerminalID id, Character value, RegexParser.SymbolBufferString src_string)
    {
        super(id, value, src_string);
    }
}
