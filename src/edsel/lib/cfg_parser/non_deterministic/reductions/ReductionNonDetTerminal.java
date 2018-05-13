package edsel.lib.cfg_parser.non_deterministic.reductions;

import edsel.lib.cfg_model.CFG_Terminal;
import lib.java_lang_extensions.tuples.Range_int;

public abstract class
ReductionNonDetTerminal
        <ENUM_TERMINAL_ID extends Enum<ENUM_TERMINAL_ID>,
                TERMINAL_VALUE_TYPE>
        implements
        Reduction
{
    CFG_Terminal<ENUM_TERMINAL_ID, TERMINAL_VALUE_TYPE>
            terminal;

    Range_int src_text;

    public abstract String print(int tree_level);
}
