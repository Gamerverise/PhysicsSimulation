package edsel.lib.cfg_parser.reductions;

import edsel.lib.io.Token;
import edsel.lib.io.TokenBuffer;

import static lib.text_io.FormattedText.spaces;

public class Reduction
        <ENUM_PRODUCTION_ID extends Enum<ENUM_PRODUCTION_ID>,
                ENUM_TOKEN_ID extends Enum<ENUM_TOKEN_ID>,
                TOKEN_VALUE_TYPE,
                TOKEN_TYPE extends
                        Token<ENUM_TOKEN_ID, TOKEN_VALUE_TYPE, TOKEN_TYPE>>
    extends ReductionBase<ENUM_TOKEN_ID, TOKEN_VALUE_TYPE, TOKEN_TYPE>
{
    public ENUM_PRODUCTION_ID production_id;
    public int branch_num;

    public ReductionBase[] sub_reductions;

    public int num_branches_explored = 0;

    public Reduction(
            ENUM_PRODUCTION_ID                              production_id,
            int                                             branch_num,
            ReductionBase[]                                 sub_reductions,
            int                                             num_branches_explored,
            TokenBuffer
                    <ENUM_TOKEN_ID,
                            TOKEN_VALUE_TYPE,
                            TOKEN_TYPE>.TokenBufferString
                                                            src_string)
    {
        super(src_string);
//        this.production = production;
        this.branch_num = branch_num;
        this.sub_reductions = sub_reductions;
        this.num_branches_explored = num_branches_explored;
    }

    // =========================================================================================

    public String print(int tree_level) {
        String string = spaces(tree_level) + production_id.toString() + ", branch " + branch_num;

        for (ReductionBase child : sub_reductions)
            string += "\n" + child.print(tree_level + 1);

        return string;
    }
}
