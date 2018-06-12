package edsel.cfgs.regex_cfg;

import edsel.lib.cfg_model.CFG_Production;

import static edsel.cfgs.regex_cfg.RegexProductionID.*;
import static edsel.cfgs.regex_cfg.RegexTerminal.*;
import static lib.java_lang_extensions.var_var_args.SubVarArgs.V;

public class RegexProduction_LALR_1 extends CFG_Production<RegexProductionID>
{
    public static RegexProduction_LALR_1 START         = new RegexProduction_LALR_1();
    public static RegexProduction_LALR_1 SUB_EXPR      = new RegexProduction_LALR_1();
    public static RegexProduction_LALR_1 GROUP         = new RegexProduction_LALR_1();
    public static RegexProduction_LALR_1 AND           = new RegexProduction_LALR_1();
    public static RegexProduction_LALR_1 OR            = new RegexProduction_LALR_1();
    public static RegexProduction_LALR_1 REPEAT        = new RegexProduction_LALR_1();

    /*

    START
        AND
        LA_1_SUB_EXPR

    AND
        LA_1_SUB_EXPR LA_1_SUB_EXPR

    OR
        LA_1_SUB_EXPR | LA_1_SUB_EXPR

    GROUP
        ( LA_1_SUB_EXPR )

    REPEAT
        LA_1_SUB_EXPR *

    LA_1_SUB_EXPR
        OR
        GROUP
        REPEAT
        LITERAL

    =====================================================================================

    START
        SUB_EXPR

    SUB_EXPR
        SUB_EXPR GROUP
        SUB_EXPR OR
        SUB_EXPR REPEAT
        SUB_EXPR LITERAL

        GROUP
        OR
        REPEAT
        LITERAL

    GROUP
        ( SUB_EXPR )

    OR
        SUB_EXPR | SUB_EXPR

    REPEAT
        SUB_EXPR *

    =====================================================================================

    START
        SUB_EXPR

    SUB_EXPR
        SUB_EXPR ( SUB_EXPR )
        SUB_EXPR SUB_EXPR | SUB_EXPR
        SUB_EXPR SUB_EXPR *
        SUB_EXPR LITERAL

        ( SUB_EXPR )
        SUB_EXPR | SUB_EXPR
        SUB_EXPR *
        LITERAL

    =====================================================================================

    START
        SUB_EXPR

    SUB_EXPR
        GROUP
        AND
        OR
        REPEAT
        LITERAL

    GROUP
        ( SUB_EXPR )

    AND
        SUB_EXPR GROUP
        SUB_EXPR OR
        SUB_EXPR REPEAT
        SUB_EXPR LITERAL

    OR
        SUB_EXPR | SUB_EXPR

    REPEAT
        SUB_EXPR *
    */

    static {
        START.init(START_ID, "start",
                V(SUB_EXPR));

        SUB_EXPR.init(SUB_EXPR_ID, "subexpr",
                V(GROUP),
                V(AND),
                V(OR),
                V(REPEAT),
                V(LITERAL));

        GROUP.init(GROUP_ID, "group",
                V(OP, SUB_EXPR, CP));

        AND.init(AND_ID, "and",
                V(SUB_EXPR, GROUP),
                V(SUB_EXPR, OR),
                V(SUB_EXPR, REPEAT),
                V(SUB_EXPR, LITERAL));

        OR.init(OR_ID, "or",
                V(SUB_EXPR, OR, SUB_EXPR));

        REPEAT.init(REPEAT_ID, "star",
                V(SUB_EXPR, REPEAT));
    }
}
