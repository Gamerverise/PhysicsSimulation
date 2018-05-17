package edsel.cfgs.regex_cfg.io;

import edsel.cfgs.regex_cfg.RegexTerminalID;
import edsel.lib.cfg_parser.RCFG_Parser.SymbolBuffer;
import edsel.lib.cfg_parser.parse_node.Token;
import lib.java_lang_extensions.parametrized_types.Instantiator;

import static edsel.cfgs.regex_cfg.RegexTerminal.*;

public class RegexSymbolBuffer extends SymbolBuffer
{
    public RegexSymbolBuffer(String filename) {
        super(filename);
    }

    // =========================================================================================

    public RegexSymbolBuffer self() {
        return this;
    }

    public RegexSymbolBuffer new_instance(Object... args) {
        return Instantiator.new_instance(RegexSymbolBuffer.class, args);
    }

    // =========================================================================================

    public Token specialized_advance() {
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

        else
            next_id = LITERAL.id;

        SymbolBufferString tok_string = new SymbolBufferString(cursor_pos, cursor_pos + 1);

        return new RegexToken(next_id, next_char, tok_string);
    }
}