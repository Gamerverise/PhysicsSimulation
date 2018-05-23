package edsel.lib.cfg_parser.parsing_restriction;

import static edsel.lib.cfg_parser.parsing_restriction.ProductionRestriction.RestrictionMode.EXACT_MODE;
import static edsel.lib.cfg_parser.parsing_restriction.ProductionRestriction.RestrictionMode.PREFIX_MODE;

public enum RestrictionOperator {

    RESTRICTION_ESCAPE('\\'),

    TERMINAL_RESTRICTION('='),

    PRODUCTION_PREFIX_RESTRICTION('~'),
    PRODUCTION_RESTRICTION('#'),
    SUFFIX_RESTRICTION('.'),        // Not yet implemented

    BRANCH_PREFIX_RESTRICTION('&'),
    BRANCH_RESTRICTION('/'),

    END_RESTRICTION(')');

    public char chr;

    RestrictionOperator(char chr) {
        this.chr = chr;
    }

    public static RestrictionOperator[] op_table = new RestrictionOperator[256];

    public static RestrictionOperator get_op(char chr) {
        return op_table[chr];
    }

    static {
        for (int i = 0; i < op_table.length; i++)
            op_table[i] = null;

        op_table[RESTRICTION_ESCAPE.chr] = RESTRICTION_ESCAPE;

        op_table[TERMINAL_RESTRICTION.chr] = TERMINAL_RESTRICTION;

        op_table[PRODUCTION_PREFIX_RESTRICTION.chr] = PRODUCTION_PREFIX_RESTRICTION;
        op_table[PRODUCTION_RESTRICTION.chr] = PRODUCTION_RESTRICTION;
        op_table[SUFFIX_RESTRICTION.chr] = SUFFIX_RESTRICTION;

        op_table[BRANCH_PREFIX_RESTRICTION.chr] = BRANCH_PREFIX_RESTRICTION;
        op_table[BRANCH_RESTRICTION.chr] = BRANCH_RESTRICTION;
    }
}
