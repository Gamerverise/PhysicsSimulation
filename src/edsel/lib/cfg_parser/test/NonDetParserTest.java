package edsel.lib.cfg_parser.test;

import edsel.cfgs.regex_cfg.RegexProductionID;
import edsel.cfgs.regex_cfg.RegexTerminalBuffer;
import edsel.cfgs.regex_cfg.RegexTerminalID;
import edsel.lib.cfg_parser.non_deterministic.CFG_NonDetParser;
import lib.java_lang_extensions.tuples.Range_int;

import static edsel.cfgs.regex_cfg.RegexProduction.START;
import static edsel.lib.cfg_parser.non_deterministic.CFG_NonDetParser.AmbiguousParserInput;

public class NonDetParserTest {
    public static void main(String[] args) {

        String filename = "C:\\home\\dad\\Gravity_Game\\Gravity_Game_IDEA\\src\\edsel\\lib\\cfg_parser\\test"
                + "\\RegexParserTestData.txt";

        RegexTerminalBuffer input = new RegexTerminalBuffer(filename);
        CFG_NonDetParser<RegexTerminalID, RegexProductionID, Range_int> parser = new CFG_NonDetParser<>();

        try {
            Range_int reduction = parser.parse_recursive(START, input);
            System.out.print(input.get_substr(reduction));

        } catch (AmbiguousParserInput e) {
            throw new RuntimeException();
        }
    }
}
