package edsel.lib.cfg_parser.lalr;

import edsel.lib.cfg_model.CFG;
import edsel.lib.cfg_model.RCFG_Production;
import edsel.lib.cfg_model.RCFG_Symbol;
import edsel.lib.cfg_model.RCFG_Terminal;
import edsel.lib.cfg_parser.transitions.AbstractStateStackEntry;

public class
LALR_ParseTable
        <TERMINAL_TYPE
                extends RCFG_Terminal
                        <TERMINAL_TYPE,
                                PRODUCTION_TYPE,
                                SYMBOL_TYPE,
                                ENUM_TERMINAL_ID,
                                TERMINAL_VALUE_TYPE,
                                ENUM_PRODUCTION_ID,
                                REDUCTION_TYPE>,
                PRODUCTION_TYPE
                        extends RCFG_Production<TERMINAL_TYPE,
                                PRODUCTION_TYPE,
                                SYMBOL_TYPE,
                                ENUM_TERMINAL_ID,
                                TERMINAL_VALUE_TYPE,
                                ENUM_PRODUCTION_ID,
                                REDUCTION_TYPE>,
                SYMBOL_TYPE
                        extends RCFG_Symbol<TERMINAL_TYPE,
                                PRODUCTION_TYPE,
                                SYMBOL_TYPE,
                                ENUM_TERMINAL_ID,
                                TERMINAL_VALUE_TYPE,
                                ENUM_PRODUCTION_ID,
                                REDUCTION_TYPE>,
                ENUM_TERMINAL_ID extends Enum<ENUM_TERMINAL_ID>,
                TERMINAL_VALUE_TYPE,
                ENUM_PRODUCTION_ID extends Enum<ENUM_PRODUCTION_ID>,
                REDUCTION_TYPE>
{
    public AbstractStateStackEntry<TERMINAL_TYPE,
                            PRODUCTION_TYPE,
                            SYMBOL_TYPE,
                            ENUM_TERMINAL_ID,
                            TERMINAL_VALUE_TYPE,
                            ENUM_PRODUCTION_ID,
                            REDUCTION_TYPE>
            start_state;

    public LALR_ParseTable
            <TERMINAL_TYPE,
                    PRODUCTION_TYPE,
                    SYMBOL_TYPE,
                    ENUM_TERMINAL_ID,
                    TERMINAL_VALUE_TYPE,
                    ENUM_PRODUCTION_ID,
                    REDUCTION_TYPE>
    build_parse_table(
            CFG
                    <TERMINAL_TYPE,
                            PRODUCTION_TYPE,
                            SYMBOL_TYPE,
                            ENUM_TERMINAL_ID,
                            TERMINAL_VALUE_TYPE,
                            ENUM_PRODUCTION_ID>
                    cfg)
    {
        // returns null if the cfg is not LALR

        return null;
    }
}
