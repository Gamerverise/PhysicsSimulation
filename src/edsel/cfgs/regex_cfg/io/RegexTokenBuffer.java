package edsel.cfgs.regex_cfg.io;

import edsel.cfgs.regex_cfg.RegexTerminalID;
import edsel.lib.io.Token;
import edsel.lib.io.TokenBuffer;

import static edsel.cfgs.regex_cfg.RegexTerminal.*;

public class
RegexTokenBuffer extends TokenBuffer<RegexTerminalID, Character>
{
    public RegexTokenBuffer(String filename) {
        super(filename);
    }

    public Token<RegexTerminalID, Character> specialized_next() {
        if (cursor_pos >= buf.length) {
            return null;
        }

        RegexTerminalID next_id;
        char next_char = (char) buf[cursor_pos];

        if (next_char == OP.character)
            next_id = OP.id;

        else if (next_char == CP.character)
            next_id = CP.id;

        else if (next_char == VB.character)
            next_id = VB.id;

        else if (next_char == ST.character)
            next_id = ST.id;

        else if (next_char == UB.character)
            next_id = UB.id;

        else
            next_id = LITERAL.id;

        TokenBufferString string = new TokenBufferString(cursor_pos, cursor_pos + 1);

        return new RegexToken(next_id, next_char, string);
    }
}
