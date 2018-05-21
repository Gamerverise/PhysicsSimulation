package edsel.cfgs.regex_cfg;

import edsel.cfgs.regex_cfg.RegexParser.RegexSymbolBuffer;
import edsel.lib.cfg_parser.CFG_Parser;
import edsel.lib.cfg_parser.exception.AmbiguousParserInput;
import edsel.lib.cfg_parser.exception.InputNotAccepted;
import edsel.lib.cfg_parser.non_deterministic.NonDetParser;
import edsel.lib.cfg_parser.parse_node.Reduction;
import lib.tokens.enums.CopyType;

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

    public Reduction<RegexProductionID>
    parse_recursive(String filename)
            throws AmbiguousParserInput, InputNotAccepted
    {
        RegexSymbolBuffer input = new RegexSymbolBuffer(filename);
        return parse_recursive(start_production, input, 0);
    }

    public static RegexParser RegexParser = new RegexParser();

    public class
    RegexSymbolBuffer extends
            CFG_Parser<RegexProductionID,
                            RegexTerminalID,
                            Character,
                            RegexSymbolBuffer>.SymbolBuffer<RegexSymbolBuffer>
    {
        public RegexSymbolBuffer(String filename)
                throws InputNotAccepted
        {
            super(filename);
        }

        public RegexSymbolBuffer(RegexSymbolBuffer buf, CopyType copy_type)
                throws InputNotAccepted
        {
            super(buf, copy_type);
        }

        // =========================================================================================

        public RegexToken token_advance() {
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

            CharBufferString tok_string = new CharBufferString(cursor_pos, cursor_pos + 1);

            return new RegexToken(next_id, next_char, tok_string);
        }
    }
}
