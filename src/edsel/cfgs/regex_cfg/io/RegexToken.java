package edsel.cfgs.regex_cfg.io;

import edsel.cfgs.regex_cfg.RegexTerminalID;
import edsel.lib.io.Token;
import edsel.lib.io.TokenBuffer;

public class RegexToken extends Token<RegexTerminalID, Character> {

    public RegexToken(
            RegexTerminalID
                    id,
            Character
                    value,
            TokenBuffer<RegexTerminalID, Character>.TokenBufferString
                    src_string)
    {
        super(id, value, src_string);
    }
}
