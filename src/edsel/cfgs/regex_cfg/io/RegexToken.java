package edsel.cfgs.regex_cfg.io;

import edsel.cfgs.regex_cfg.RegexTerminalID;
import edsel.lib.io.Token;

public class RegexToken
    extends Token<RegexTerminalID, Character>
{
    public RegexToken(RegexTerminalID id, Character chr, int src_start, int src_end) {
        super(id, chr, src_start, src_end);
    }
}
