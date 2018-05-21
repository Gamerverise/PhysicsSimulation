package edsel.cfgs.regex_cfg;

import edsel.lib.cfg_parser.parse_node.Token;
import edsel.lib.io.CharBuffer;

public class RegexToken extends Token<RegexTerminalID, Character> {

    public RegexToken(
            RegexTerminalID                                     id,
            Character                                           value,
            CharBuffer<? extends CharBuffer>.CharBufferString   src_string)
    {
        super(id, value, src_string);
    }
}
