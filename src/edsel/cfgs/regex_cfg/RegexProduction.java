package edsel.cfgs.regex_cfg;

import edsel.lib.cfg_model.CFG_Symbol;
import edsel.lib.cfg_model.RCFG_Production;
import edsel.lib.cfg_parser.reductions.Reduction;
import edsel.lib.cfg_parser.reductions.ReductionProduction;
import edsel.lib.io.TokenBuffer.TokenBufferString;

import static edsel.cfgs.regex_cfg.RegexProductionID.*;
import static edsel.cfgs.regex_cfg.RegexTerminal.*;
import static lib.java_lang_extensions.var_var_args.SubVarArgs.V;

public class RegexProduction
        extends RCFG_Production<RegexProductionID, ReductionProduction<RegexProduction>>
{
    public RegexProduction() {
        super();
    }

    public RegexProduction(RegexProductionID id, CFG_Symbol[]... rhs) {
        super.init(id, rhs);
    }

    public ReductionProduction reduce (
            int branch_num,
            Reduction[] sub_reductions,
            int num_branches_explored,
            TokenBufferString src_string)
    {
        return new ReductionProduction(this, branch_num, sub_reductions, num_branches_explored, src_string);
    }

    // =========================================================================================

    public static RegexProduction START         = new RegexProduction();
    public static RegexProduction SUB_EXPR      = new RegexProduction();
    public static RegexProduction GROUP         = new RegexProduction();
    public static RegexProduction AND           = new RegexProduction();
    public static RegexProduction OR            = new RegexProduction();
    public static RegexProduction REPEAT        = new RegexProduction();

    static {
        START.init(START_ID,
                V(SUB_EXPR));

        SUB_EXPR.init(SUB_EXPR_ID,
                V(GROUP),
                V(AND),
                V(OR),
                V(REPEAT),
                V(LITERAL));

        GROUP.init(GROUP_ID,
                V(OP, SUB_EXPR, CP));

        AND.init(AND_ID,
                V(SUB_EXPR, SUB_EXPR));

        OR.init(OR_ID,
                V(SUB_EXPR, OR, SUB_EXPR));

        REPEAT.init(REPEAT_ID,
                V(SUB_EXPR, REPEAT));
    }
}
