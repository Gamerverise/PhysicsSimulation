package edsel.cfgs.regex_cfg;

import edsel.cfgs.regex_cfg.RegexParser.RegexSymbolBuffer;
import edsel.lib.cfg_parser.non_deterministic.NonDetParser;
import edsel.lib.cfg_parser.non_deterministic.NonDetParsingState;

import static edsel.cfgs.regex_cfg.RegexProduction.*;
import static edsel.cfgs.regex_cfg.RegexTerminal.*;
import static lib.java_lang_extensions.var_var_args.SubVarArgs.V;

public class RegexParser extends
        NonDetParser<RegexProductionID, RegexTerminalID, Character, RegexSymbolBuffer>
{
    public RegexParser()
    {
        super(
                START,
                V(SUB_EXPR, GROUP, AND, OR, REPEAT),
                OP, CP, VB, ST, UB, LITERAL);
    }

    public RegexSymbolBuffer get_new_input(String filename) {
        return new RegexSymbolBuffer(filename);
    }

    public
    NonDetParsingState<RegexProductionID, RegexTerminalID, Character, RegexSymbolBuffer>
    get_new_parsing_state(RegexSymbolBuffer input)
    {
        return new NonDetParsingState<>(input);
    }

    public static RegexParser RegexParser = new RegexParser();

    public class
    RegexSymbolBuffer extends
            SymbolBuffer
    {
        public RegexSymbolBuffer(String filename)
        {
            super(filename);
        }

        // =========================================================================================

        public RegexToken next_token() {
            if (cursor_pos >= buf.length) {
                return null;
            }

            char next_char = (char) buf[cursor_pos];

            CharBufferString tok_string = new CharBufferString(cursor_pos, cursor_pos + 1);
            cursor_pos++;

            if (next_char == OP.character)
                return OP.reduce(tok_string);

            else if (next_char == CP.character)
                return CP.reduce(tok_string);

            else if (next_char == VB.character)
                return VB.reduce(tok_string);

            else if (next_char == ST.character)
                return ST.reduce(tok_string);

            else if (next_char == UB.character)
                return UB.reduce(tok_string);
            else
                return LITERAL.reduce(tok_string);
        }
    }
}
