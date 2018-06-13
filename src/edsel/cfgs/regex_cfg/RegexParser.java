package edsel.cfgs.regex_cfg;

import edsel.cfgs.regex_cfg.RegexParser.RegexSymbolBuffer;
import edsel.lib.cfg_model.CFG_Production;
import edsel.lib.cfg_parser.CFG_Parser;
import edsel.lib.cfg_parser.exception.InputNotAccepted;
import edsel.lib.cfg_parser.non_deterministic.NonDetParser;
import edsel.lib.cfg_parser.non_deterministic.NonDetParsingState;

import static edsel.cfgs.regex_cfg.RegexProduction.*;
import static edsel.cfgs.regex_cfg.RegexTerminal.*;

public class RegexParser extends
        NonDetParser<RegexProductionID, RegexTerminalID, Character, RegexSymbolBuffer,
                NonDetParsingState<RegexProductionID, RegexTerminalID, Character, RegexSymbolBuffer>>
{
    public RegexParser(CFG_Production<RegexProductionID>... productions)
    {
        super(
                START,
                productions,
                OP, CP, VB, ST, UB, LITERAL);
    }

    public RegexSymbolBuffer get_new_input(String filename)
            throws InputNotAccepted
    {
        return new RegexSymbolBuffer(filename);
    }

    public
    NonDetParsingState<RegexProductionID, RegexTerminalID, Character, RegexSymbolBuffer>
    get_new_parsing_state(RegexSymbolBuffer input)
    {
        return new NonDetParsingState<>(input);
    }

    public static RegexParser RegexParser = new RegexParser(SUB_EXPR, GROUP, AND, OR, REPEAT);

    public static RegexParser RegexParser_LALR_1
            =
            new RegexParser(
                    RegexProduction_LALR_1.SUB_EXPR,
                    RegexProduction_LALR_1.GROUP,
                    RegexProduction_LALR_1.AND,
                    RegexProduction_LALR_1.OR,
                    RegexProduction_LALR_1.REPEAT);

    public class
    RegexSymbolBuffer extends
            CFG_Parser
                    <RegexProductionID,
                            RegexTerminalID,
                            Character,
                            RegexSymbolBuffer,
                            NonDetParsingState
                                    <RegexProductionID, RegexTerminalID, Character, RegexSymbolBuffer>>.SymbolBuffer
    {
        public RegexSymbolBuffer(String filename)
                throws InputNotAccepted
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
