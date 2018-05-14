package edsel.cfgs.regex_cfg.io;

import edsel.cfgs.regex_cfg.RegexTerminalID;
import edsel.lib.io.Token;
import edsel.lib.io.TokenBuffer.TokenBufferString;

public class RegexToken
    extends Token<RegexTerminalID, Character>
{
    public RegexToken(RegexTerminalID id, Character chr, TokenBufferString string) {
        super(id, chr, string);
    }
}
