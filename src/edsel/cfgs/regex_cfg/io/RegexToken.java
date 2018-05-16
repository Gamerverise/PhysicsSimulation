package edsel.cfgs.regex_cfg.io;

import edsel.cfgs.regex_cfg.RegexTerminalID;
import edsel.lib.io.ParseNodeBuffer;

public class RegexToken extends Token<RegexTerminalID, Character> {

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
