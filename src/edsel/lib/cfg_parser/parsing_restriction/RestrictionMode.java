package edsel.lib.cfg_parser.parsing_restriction;

public enum RestrictionMode {
    TERMINAL_RESTRICTION('='),
    PREFIX_RESTRICTION('~'),
    SYMBOL_RESTRICTION('#'),
    SUFFIX_RESTRICTION('.'),     // Not yet implemented
    BRANCH_RESTRICTION('/');     // Not yet implemented

    public char chr;

    RestrictionMode(char chr) {
        this.chr = chr;
    }

    public static RestrictionMode[] mode_table = new RestrictionMode[256];

    public static RestrictionMode get_mode(char chr) {
        return mode_table[chr];
    }

    static {
        for (int i = 0; i < mode_table.length; i++)
            mode_table[i] = null;

        mode_table[TERMINAL_RESTRICTION.chr] = TERMINAL_RESTRICTION;
        mode_table[PREFIX_RESTRICTION.chr] = PREFIX_RESTRICTION;
        mode_table[SYMBOL_RESTRICTION.chr] = SYMBOL_RESTRICTION;
        mode_table[SUFFIX_RESTRICTION.chr] = SUFFIX_RESTRICTION;
        mode_table[BRANCH_RESTRICTION.chr] = BRANCH_RESTRICTION;
    }
}
