package edsel.cfgs.regex_cfg;

import edsel.lib.io.CFG_TerminalBuffer;

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

        Character next_value = (char) buf[cur_tok_start];

        if (next_value == OP.value)
            next_terminal.id = OP.id;

        else if (next_value == CP.value)
            next_terminal.id = CP.id;

        else if (next_value == VB.value)
            next_terminal.id = VB.id;

        else if (next_value == ST.value)
            next_terminal.id = ST.id;

        else if (next_value == UB.value)
            next_terminal.id = UB.id;

        else
            next_terminal.id = LITERAL.id;
    }
}
