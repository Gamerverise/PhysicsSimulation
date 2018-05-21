package edsel.lib.cfg_parser.test;

import edsel.cfgs.regex_cfg.RegexParser;
import edsel.cfgs.regex_cfg.RegexProductionID;
import edsel.lib.cfg_parser.exception.InputNotAccepted;
import edsel.lib.cfg_parser.parse_node.Reduction;

import edsel.lib.cfg_parser.exception.AmbiguousParserInput;

public class NonDetParserTest {
    public static void main(String[] args) {

        String filename = "C:\\home\\dad\\Gravity_Game\\Gravity_Game_IDEA\\src\\edsel\\lib\\cfg_parser\\test"
                + "\\RegexParserTestData.txt";

        RegexParser parser = new RegexParser();

        try {
            Reduction<RegexProductionID> reduction;

            reduction = parser.parse_recursive(filename);

            if (reduction != null)
                System.out.print(reduction.print(0));

        } catch (AmbiguousParserInput | InputNotAccepted e) {
            throw new RuntimeException();
        }
    }
}
