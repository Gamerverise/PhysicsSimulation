package edsel.lib.cfg_parser.reductions;

import edsel.lib.cfg_model.RCFG_Production;
import edsel.lib.io.TokenBuffer.TokenBufferString;

import static lib.text_io.FormattedText.spaces;

public class ReductionProduction
        <ENUM_PRODUCTION_ID extends Enum<ENUM_PRODUCTION_ID>,
                PRODUCTION_TYPE extends
                        RCFG_Production<ENUM_PRODUCTION_ID>>
    extends Reduction
{
    public PRODUCTION_TYPE production;
    public int branch_num;

    public Reduction[] sub_reductions;

    public int num_branches_explored = 0;

    public ReductionProduction(
            PRODUCTION_TYPE production,
            int branch_num,
            Reduction[] sub_reductions,
            int num_branches_explored,
            TokenBufferString src_string)
    {
        super(src_string);
        this.production = production;
        this.branch_num = branch_num;
        this.sub_reductions = sub_reductions;
        this.num_branches_explored = num_branches_explored;
    }

    public String print(int tree_level) {
        String string = spaces(tree_level) + production.id.toString() + ", branch " + branch_num;

        for (Reduction child : sub_reductions)
            string += "\n" + child.print(tree_level + 1);

        return string;
    }
}
