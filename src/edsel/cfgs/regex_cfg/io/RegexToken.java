package edsel.cfgs.regex_cfg.io;

import edsel.cfgs.regex_cfg.RegexTerminalID;
import edsel.lib.cfg_parser.RCFG_Parser.SymbolBuffer.SymbolBufferString;
import edsel.lib.cfg_parser.parse_node.Token;

public class RegexToken extends Token<RegexTerminalID, Character> {

    public RegexToken(RegexTerminalID id, Character value, SymbolBufferString src_string)
    {
        super(id, value, src_string);
    }
}
