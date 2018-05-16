package edsel.cfgs.regex_cfg.io;

import edsel.cfgs.regex_cfg.RegexTerminalID;
import edsel.lib.cfg_parser.parse_node.ParseNode;
import edsel.lib.io.old.ParseNodeBuffer;
import lib.java_lang_extensions.parametrized_types.Instantiator;

import static edsel.cfgs.regex_cfg.RegexTerminal.*;

public class RegexParseNodeBuffer extends ParseNodeBuffer
{
    public RegexParseNodeBuffer(String filename) {
        super(filename);
    }

    // =========================================================================================

    public RegexParseNodeBuffer self() {
        return this;
    }

    public RegexParseNodeBuffer new_instance(Object... args) {
        return Instantiator.new_instance(RegexParseNodeBuffer.class, args);
    }

    // =========================================================================================

    public ParseNode specialized_advance() {
        if (cursor_pos >= buf.length) {
            return null;
        }

        char next_char = (char) buf[cursor_pos];

        RegexTerminalID next_id;

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

        else if (next_char == GATE.character)
            next_id = GATE.id;

        else
            next_id = LITERAL.id;

        ParseNodeBufferString tok_string = new ParseNodeBufferString(cursor_pos, cursor_pos + 1);

        return new RegexToken(next_id, next_char, tok_string);
    }
}
