package edsel.lib.cfg_parser.test;

import edsel.cfgs.regex_cfg.RegexParser;
import edsel.cfgs.regex_cfg.RegexProductionID;
import edsel.lib.cfg_parser.exception.InputNotAccepted;
import edsel.lib.cfg_parser.parse_node.Reduction;

import edsel.lib.cfg_parser.exception.AmbiguousParserInput;
import lib.java_lang_extensions.mutable.MutableInt;

public class NonDetParserTest {
    public static void main(String[] args) {

        String filename = "C:\\home\\dad\\Gravity_Game\\Gravity_Game_IDEA\\src\\edsel\\lib\\cfg_parser\\test"
                + "\\RegexParserTestData.txt";

        try {
            Reduction<RegexProductionID> reduction;

            reduction = RegexParser.RegexParser.parse_recursive(filename);

            if (reduction != null)
                System.out.print(reduction.print(0));

        } catch (AmbiguousParserInput | InputNotAccepted e) {
            throw new RuntimeException();
        }
    }
}
