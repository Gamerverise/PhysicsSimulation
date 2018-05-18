package edsel.lib.cfg_parser.test;

import edsel.cfgs.regex_cfg.RegexParser;
import edsel.cfgs.regex_cfg.RegexProductionID;
import edsel.cfgs.regex_cfg.RegexTerminalID;
import edsel.lib.cfg_parser.non_deterministic.NonDetParser;
import edsel.lib.cfg_parser.parse_node.Reduction;

import static edsel.cfgs.regex_cfg.RegexParser.RegexParser;

import edsel.lib.cfg_parser.exception.AmbiguousParserInput;

public class NonDetParserTest {
    public static void main(String[] args) {

        String filename = "C:\\home\\dad\\Gravity_Game\\Gravity_Game_IDEA\\src\\edsel\\lib\\cfg_parser\\test"
                + "\\RegexParserTestData.txt";

        edsel.cfgs.regex_cfg.RegexParser.RegexSymbolBuffer input = new RegexParser.RegexSymbolBuffer(filename);

        NonDetParser<RegexProductionID, RegexTerminalID, Character>
                parser = new NonDetParser<>();

        try {
            Reduction<RegexProductionID> reduction;

            reduction = parser.parse_recursive(RegexParser, input);

            if (reduction != null)
                System.out.print(reduction.print(0));

        } catch (AmbiguousParserInput e) {
            throw new RuntimeException();
        }
    }
}
