package edsel.lib.cfg_parser.test;

import edsel.cfgs.regex_cfg.RegexProductionID;
import edsel.cfgs.regex_cfg.RegexTerminalID;
import edsel.cfgs.regex_cfg.io.RegexTokenBuffer;
import edsel.lib.cfg_parser.non_deterministic.NonDetParser;
import edsel.lib.cfg_parser.reductions.Reduction;

import static edsel.cfgs.regex_cfg.RegexProduction.START;
import static edsel.lib.cfg_parser.non_deterministic.NonDetParser.AmbiguousParserInput;

public class NonDetParserTest {
    public static void main(String[] args) {

        String filename = "C:\\home\\dad\\Gravity_Game\\Gravity_Game_IDEA\\src\\edsel\\lib\\cfg_parser\\test"
                + "\\RegexParserTestData.txt";

        RegexTokenBuffer input = new RegexTokenBuffer(filename);
        NonDetParser<RegexProductionID, RegexTerminalID, Character> parser = new NonDetParser<>();

        try {
             Reduction reduction = parser.parse_recursive(START, input, 0);

            if (reduction != null)
                System.out.print(reduction.print(0));

        } catch (AmbiguousParserInput e) {
            throw new RuntimeException();
        }
    }
}
