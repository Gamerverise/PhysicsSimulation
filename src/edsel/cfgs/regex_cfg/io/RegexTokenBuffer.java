package edsel.cfgs.regex_cfg.io;

import edsel.cfgs.regex_cfg.RegexTerminalID;
import edsel.lib.io.Token;
import edsel.lib.io.TokenBuffer;
import lib.java_lang_extensions.parametrized_types.Instantiator;

import static edsel.cfgs.regex_cfg.RegexTerminal.*;
import static edsel.cfgs.regex_cfg.RegexTerminalID.RESTRICT_ID;
import static edsel.cfgs.regex_cfg.RegexTerminalID.UNRESTRICT_ID;

public class RegexTokenBuffer extends
        TokenBuffer<RegexTerminalID, Character, RegexToken>
{
    public int restricted_mode_nesting = 0;

    public RegexTokenBuffer(String filename) {
        super(filename);
    }

    // =========================================================================================

    public RegexTokenBuffer self() {
        return this;
    }

    public RegexTokenBuffer new_instance(Object... args) {
        return Instantiator.new_instance(RegexTokenBuffer.class, args);
    }

    // =========================================================================================

    public Token<RegexTerminalID, Character, RegexToken> specialized_next() {
        if (cursor_pos >= buf.length) {
            return null;
        }

        char next_char = (char) buf[cursor_pos];

        if (next_char == '(') {

            if (cursor_pos + 1 < buf.length) {

                char next_next_char = (char) buf[cursor_pos + 1];

                if (next_next_char == '*') {

                    int restriction_name_start = cursor_pos;

                    while (cursor_pos < buf.length && buf[cursor_pos] != ':')
                        cursor_pos++;

                    TokenBufferString restriction_name
                            = new TokenBufferString(restriction_name_start, cursor_pos);

                    cursor_pos++;
                    restricted_mode_nesting++;

                    return new RegexToken(RESTRICT_ID, (char) -1 , restriction_name);
                }
            }
        } else if (restricted_mode_nesting > 0 && next_char == '*')

            if (cursor_pos + 1 < buf.length) {

                char next_next_char = (char) buf[cursor_pos];

                if (next_next_char == ')') {
                    TokenBufferString tok_string = new TokenBufferString(cursor_pos, cursor_pos + 1);
                    cursor_pos++;
                    restricted_mode_nesting--;
                    return new RegexToken(UNRESTRICT_ID, (char) -1, tok_string);
                }
            }

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

        TokenBufferString tok_string = new TokenBufferString(cursor_pos, cursor_pos + 1);

        return new RegexToken(next_id, next_char, tok_string);
    }
}
