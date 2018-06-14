package edsel.lib.cfg_parser.test;

import edsel.cfgs.regex_cfg.RegexProductionID;
import edsel.lib.cfg_parser.exception.AmbiguousParserInput;
import edsel.lib.cfg_parser.exception.InputNotAccepted;
import edsel.lib.cfg_parser.parse_node.Reduction;

import static edsel.cfgs.regex_cfg.RegexCFG.RegexCFG_LALR_1;
import static edsel.cfgs.regex_cfg.RegexParser.RegexParser_LALR_1;

public class NonDetParserTest {
    public static void main(String[] args) {

        String filename = "C:\\home\\dad\\Gravity_Game\\Gravity_Game_IDEA\\src\\edsel\\lib\\cfg_parser\\test"
                + "\\RegexParserTestData.txt";

        try {
            Reduction<RegexProductionID> reduction;

            System.out.print("\nParsing: " + filename + "\n\n");

            reduction = RegexParser_LALR_1.parse_recursive(RegexCFG_LALR_1, filename);

            if (reduction != null)
                System.out.print("\nFinished parsing: " + filename);

        } catch (AmbiguousParserInput | InputNotAccepted e) {
            throw new RuntimeException();
        }
    }
}
