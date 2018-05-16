package edsel.lib.cfg_parser.test;

import edsel.cfgs.regex_cfg.RegexProductionID;
import edsel.cfgs.regex_cfg.RegexTerminalID;
import edsel.cfgs.regex_cfg.io.RegexParseNodeBuffer;
import edsel.lib.cfg_parser.non_deterministic.NonDetParser;
import edsel.lib.cfg_parser.parse_node.ReductionParseNode;

import static edsel.cfgs.regex_cfg.RegexRCFG.RegexRCFG;
import static edsel.lib.cfg_parser.non_deterministic.NonDetParser.AmbiguousParserInput;

public class NonDetParserTest {
    public static void main(String[] args) {

        String filename = "C:\\home\\dad\\Gravity_Game\\Gravity_Game_IDEA\\src\\edsel\\lib\\cfg_parser\\test"
                + "\\RegexParserTestData.txt";

        RegexParseNodeBuffer input = new RegexParseNodeBuffer(filename);

        NonDetParser<RegexProductionID, RegexTerminalID, Character>
                parser = new NonDetParser<>();

        try {
            ReductionParseNode<RegexProductionID> reduction;

            reduction = parser.parse_recursive(RegexRCFG, input);

            if (reduction != null)
                System.out.print(reduction.print(0));

        } catch (AmbiguousParserInput e) {
            throw new RuntimeException();
        }
    }
}
