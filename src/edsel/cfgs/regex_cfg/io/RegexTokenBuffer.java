package edsel.cfgs.regex_cfg.io;

import edsel.cfgs.regex_cfg.RegexTerminalID;
import edsel.lib.io.TokenBuffer;

import static edsel.cfgs.regex_cfg.RegexTerminal.*;

public class
RegexTokenBuffer extends TokenBuffer<RegexToken>
{
    public RegexTokenBuffer(String filename) {
        super(filename);
    }

    public void update_next() {
        if (cur_tok_start >= buf.length) {
            next_token = null;
            return;
        }

        RegexTerminalID next_id;
        char next_char = (char) buf[cur_tok_start];
        String next_char_string = new String(buf, cur_tok_start, 1);

        if (next_char_string == OP.pattern)
            next_id = OP.id;

        else if (next_char_string == CP.pattern)
            next_id = CP.id;

        else if (next_char_string == VB.pattern)
            next_id = VB.id;

        else if (next_char_string == ST.pattern)
            next_id = ST.id;

        else if (next_char_string == UB.pattern)
            next_id = UB.id;

        else
            next_id = LITERAL.id;

        next_token = new RegexToken(next_id, next_char, cur_tok_start, cur_tok_end);
    }
}
