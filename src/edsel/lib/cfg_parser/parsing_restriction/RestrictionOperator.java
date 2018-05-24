package edsel.lib.cfg_parser.parsing_restriction;

public enum RestrictionOperator {

    RESTRICTION_ESCAPE('\\'),

    GATE_RESTRICTION('#'),

    TERMINAL_RESTRICTION('='),

    PRODUCTION_PREFIX_RESTRICTION('~'),
    PRODUCTION_EXACT_RESTRICTION('#'),
    PRODUCTION_SUFFIX_RESTRICTION('.'),     // Not yet implemented

    BRANCH_PREFIX_RESTRICTION('&'),
    BRANCH_EXACT_RESTRICTION('/'),
    BRANCH_SUFFIX_RESTRICTION('.'),         // Not yet implemented

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
        op_table[PRODUCTION_EXACT_RESTRICTION.chr] = PRODUCTION_EXACT_RESTRICTION;
        op_table[BRANCH_SUFFIX_RESTRICTION.chr] = BRANCH_SUFFIX_RESTRICTION;

        op_table[BRANCH_PREFIX_RESTRICTION.chr] = BRANCH_PREFIX_RESTRICTION;
        op_table[BRANCH_EXACT_RESTRICTION.chr] = BRANCH_EXACT_RESTRICTION;
    }
}
