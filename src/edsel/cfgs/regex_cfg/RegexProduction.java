package edsel.cfgs.regex_cfg;

import edsel.cfgs.regex_cfg.reductions.RegexReduction;
import edsel.lib.cfg_model.CFG_Symbol;
import edsel.lib.cfg_model.CFG_Terminal;
import edsel.lib.cfg_model.RCFG_Production;
import lib.java_lang_extensions.tuples.Range_int;

import static edsel.cfgs.regex_cfg.RegexProductionID.*;
import static edsel.cfgs.regex_cfg.RegexTerminal.*;
import static lib.java_lang_extensions.var_var_args.SubVarArgs.V;

public class RegexProduction
        extends RCFG_Production<RegexTerminalID, RegexProductionID, RegexReduction>
{
    public RegexProduction() {}

    public RegexProduction(RegexProductionID id, CFG_Symbol[]... rhs)
    {
        super(id, rhs);
    }

    // =========================================================================================

    public RegexReduction reduce(CFG_Symbol[] branch, Range_int[] sub_reductions) {
        Range_int last_sub_reduction = sub_reductions[sub_reductions.length - 1];

        return new Range_int(sub_reductions[0].start, last_sub_reduction.end);
    }

    public RegexReduction reduce(CFG_Terminal<RegexTerminalID, Range_int> terminal) {
        return new Range_int(terminal.value.start, terminal.value.end);
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
