package edsel.cfgs.regex_cfg;

import edsel.lib.cfg_model.CFG_Symbol;
import edsel.lib.cfg_model.CFG_Terminal;
import edsel.lib.cfg_model.RCFG_Production;
import lib.java_lang_extensions.tuples.Range_int;

import static edsel.cfgs.regex_cfg.RegexProductionID.*;
import static edsel.cfgs.regex_cfg.RegexTerminal.*;
import static lib.java_lang_extensions.var_var_args.SubVarArgs.V;

public class RegexProduction
        extends RCFG_Production<RegexTerminalID, Range_int, RegexProductionID, Range_int>
{
    public RegexProduction(RegexProductionID id, CFG_Symbol[]... rhs)
    {
        super(id, rhs);
    }

    // =========================================================================================

    public Range_int reduce(CFG_Symbol[] branch, Range_int[] sub_reductions) {
        Range_int last_sub_reduction = sub_reductions[sub_reductions.length - 1];

        return new Range_int(sub_reductions[0].start, last_sub_reduction.end);
    }

    public Range_int reduce(CFG_Terminal<RegexTerminalID, Range_int> terminal) {
        return terminal.value;
    }

    // =========================================================================================

    public static RegexProduction START;
    public static RegexProduction SUB_EXPR;
    public static RegexProduction GROUP;
    public static RegexProduction AND;
    public static RegexProduction OR;
    public static RegexProduction REPEAT;

    {
        START = new RegexProduction(START_ID,
                V(SUB_EXPR));

        SUB_EXPR = new RegexProduction(SUB_EXPR_ID,
                V(GROUP),
                V(AND),
                V(OR),
                V(REPEAT),
                V(LITERAL));

        GROUP = new RegexProduction(GROUP_ID,
                V(OP, SUB_EXPR, CP));

        AND = new RegexProduction(AND_ID,
                V(SUB_EXPR, SUB_EXPR));

        OR = new RegexProduction(OR_ID,
                V(SUB_EXPR, OR, SUB_EXPR));

        REPEAT = new RegexProduction(REPEAT_ID,
                V(SUB_EXPR, REPEAT));
    }
}
