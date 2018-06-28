package edsel.cfgs.regex_cfg;

import edsel.lib.cfg_parser.CFG_Parser;
import edsel.lib.cfg_parser.exception.InputNotAccepted;
import edsel.lib.cfg_parser.non_deterministic.NonDetParser;
import edsel.lib.cfg_parser.non_deterministic.NonDetParsingState;

import static edsel.cfgs.regex_cfg.RegexCFG.RegexCFG;
import static edsel.cfgs.regex_cfg.RegexCFG.RegexCFG_LALR_1;
import static edsel.cfgs.regex_cfg.RegexTerminal.*;

public class RegexParser extends
        NonDetParser<RegexProductionID, RegexTerminalID, Character>
{
    public static RegexParser RegexParser = new RegexParser(RegexCFG);

    public static RegexParser RegexParser_LALR_1 = new RegexParser(RegexCFG_LALR_1);

    public RegexParser(RegexCFG cfg)
    {
        super(cfg);
    }

    public RegexSymbolBuffer get_new_input(String filename)
            throws InputNotAccepted
    {
        return new RegexSymbolBuffer(filename);
    }

    public
    NonDetParsingState<RegexProductionID, RegexTerminalID, Character>
    get_new_parsing_state(RegexSymbolBuffer input)
    {
        return new NonDetParsingState<>(input);
    }

    public class
    RegexSymbolBuffer extends
            CFG_Parser
                    <RegexProductionID,
                            RegexTerminalID,
                            Character>.SymbolBuffer
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
