package edsel.cfgs.regex_cfg.io;

import edsel.cfgs.regex_cfg.RegexTerminalID;
import edsel.lib.cfg_model.CFG_Terminal;
import edsel.lib.io.CFG_TerminalBuffer;
import lib.java_lang_extensions.tuples.Range_int;

import static edsel.cfgs.regex_cfg.RegexTerminal.*;

public class
RegexTerminalBuffer extends CFG_TerminalBuffer<RegexTerminalID>
{
    public RegexTerminalBuffer(String filename) {
        super(filename);
    }

    public void update_next() {
        if (cur_tok_start >= buf.length) {
            next_terminal = null;
            return;
        }

        RegexTerminalID new_id;
        Range_int new_range = new Range_int(cur_tok_start, cur_tok_end);

        Character next_value = (char) buf[cur_tok_start];

        if (next_value == OP.value)
            new_id = OP.id;

        else if (next_value == CP.value)
            new_id = CP.id;

        else if (next_value == VB.value)
            new_id = VB.id;

        else if (next_value == ST.value)
            new_id = ST.id;

        else if (next_value == UB.value)
            new_id = UB.id;

        else
            new_id = LITERAL.id;

        next_terminal = new CFG_Terminal<>(new_id, new_range);
    }
}
