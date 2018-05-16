package edsel.cfgs.regex_cfg.io;

import edsel.cfgs.regex_cfg.RegexTerminalID;
import edsel.lib.cfg_parser.parse_node.TokenParseNode;
import edsel.lib.io.old.ParseNodeBuffer;

public class RegexToken extends TokenParseNode<RegexTerminalID, Character> {

    public RegexToken(
            RegexTerminalID
                    id,
            Character
                    value,
            ParseNodeBuffer.ParseNodeBufferString
                    src_string)
    {
        super(id, value, src_string);
    }
}
