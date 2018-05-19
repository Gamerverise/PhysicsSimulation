package edsel.lib.cfg_parser.parse_node;

import edsel.lib.io.CharBuffer.CharBufferString;
import lib.java_lang_extensions.parametrized_types.Instantiator;
import lib.tokens.enums.CopyType;

import static lib.text_io.FormattedText.spaces;
import static lib.tokens.enums.CopyType.COPY_DEEP;
import static lib.tokens.enums.CopyType.COPY_SHALLOW;

public class Reduction<ENUM_PRODUCTION_ID extends Enum<ENUM_PRODUCTION_ID>>
        extends ParseNode
{
    public ENUM_PRODUCTION_ID production_id;
    public int branch_num;

    public ParseNode[] sub_reductions;

    public int num_branches_explored = 0;

    public Reduction(
            ENUM_PRODUCTION_ID          production_id,
            int                         branch_num,
            ParseNode[]                 sub_reductions,
            int                         num_branches_explored,
            CharBufferString            src_string)
    {
        super(src_string);
        this.production_id = production_id;
        this.branch_num = branch_num;
        this.sub_reductions = sub_reductions;
        this.num_branches_explored = num_branches_explored;
    }

    public Reduction(
            Reduction<ENUM_PRODUCTION_ID> parse_node,
            CopyType copy_type)
    {
        super(copy_type == COPY_DEEP
                ? parse_node.src_string.new_copy(copy_type)
                : parse_node.src_string);

        this.production_id = parse_node.production_id;
        this.branch_num = parse_node.branch_num;
        this.num_branches_explored = parse_node.num_branches_explored;

        if (copy_type == COPY_SHALLOW)
            this.sub_reductions = parse_node.sub_reductions;
        else {
            int num_sub_reductions = parse_node.sub_reductions.length;

            this.sub_reductions = new ParseNode[num_sub_reductions];

            for (int i = 0; i < num_sub_reductions; i++)
                this.sub_reductions[i] = parse_node.sub_reductions[i].new_copy(COPY_DEEP);
        }
    }

    // =========================================================================================

    public String print(int tree_level) {
        String string = spaces(tree_level) + production_id.toString() + ", branch " + branch_num;

        for (ParseNode child : sub_reductions)
            string += "\n" + child.print(tree_level + 1);

        return string;
    }
}
