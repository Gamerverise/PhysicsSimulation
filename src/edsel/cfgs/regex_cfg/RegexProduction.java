package edsel.cfgs.regex_cfg;

import edsel.lib.cfg_model.RCFG_Production;

import static edsel.cfgs.regex_cfg.RegexProductionID.*;
import static edsel.cfgs.regex_cfg.RegexTerminal.*;
import static lib.java_lang_extensions.var_var_args.SubVarArgs.V;

public class RegexProduction extends
        RCFG_Production
                <RegexProductionID,
                        RegexTerminalID,
                        Character>
{
    public static RegexProduction START         = new RegexProduction();
    public static RegexProduction SUB_EXPR      = new RegexProduction();
    public static RegexProduction GROUP         = new RegexProduction();
    public static RegexProduction AND           = new RegexProduction();
    public static RegexProduction OR            = new RegexProduction();
    public static RegexProduction REPEAT        = new RegexProduction();

    static {
        START.init(START_ID,
                V(SUB_EXPR));

        SUB_EXPR.init(SUB_EXPR_ID, "subexpr",
                V(LITERAL),
                V(GROUP),
                V(AND),
                V(OR),
                V(REPEAT));

        GROUP.init(GROUP_ID, "group",
                V(OP, SUB_EXPR, CP));

        AND.init(AND_ID, "and",
                V(SUB_EXPR, SUB_EXPR));

        OR.init(OR_ID, "or",
                V(SUB_EXPR, OR, SUB_EXPR));

        REPEAT.init(REPEAT_ID, "star",
                V(SUB_EXPR, REPEAT));
    }
}
