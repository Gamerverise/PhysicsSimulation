package edsel.cfgs.regex_cfg.reductions;

import static lib.text_io.FormattedText.spaces;

public class RegexReduction extends ReductionNonDetTerminal {

    public String print_parse_tree(int tree_level) {
        String str = new String();

        for (RegexReduction child : parse_tree_children)
            str += child.print_parse_tree(tree_level + 1);

        return spaces(tree_level) + production.id.toString() + str;
    }
}
