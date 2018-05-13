package edsel.lib.cfg_parser.non_deterministic.reductions;

import edsel.lib.cfg_model.RCFG_Production;
import lib.java_lang_extensions.tuples.Range_int;

public abstract class ReductionNonDetProduction
        <ENUM_TERMINAL_ID extends Enum<ENUM_TERMINAL_ID>,
                TERMINAL_VALUE_TYPE,
                ENUM_PRODUCTION_ID extends Enum<ENUM_PRODUCTION_ID>>
        implements
        Reduction
{
    RCFG_Production<ENUM_PRODUCTION_ID,
                        ENUM_PRODUCTION_ID,
                        ReductionNonDetProduction<ENUM_TERMINAL_ID, TERMINAL_VALUE_TYPE, ENUM_PRODUCTION_ID>>
            production;

    int branch_num;

    ReductionNonDetProduction<ENUM_TERMINAL_ID, TERMINAL_VALUE_TYPE, ENUM_PRODUCTION_ID>[]
            parse_tree_children;

    Range_int src_text;

    public int num_branches_explored = 0;

    public abstract String print(int tree_level);
}
