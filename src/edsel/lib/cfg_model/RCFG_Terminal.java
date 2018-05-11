package edsel.lib.cfg_model;

public abstract
class RCFG_Terminal
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
                        extends RCFG_Production
                                <TERMINAL_TYPE,
                                        PRODUCTION_TYPE,
                                        SYMBOL_TYPE,
                                        ENUM_TERMINAL_ID,
                                        TERMINAL_VALUE_TYPE,
                                        ENUM_PRODUCTION_ID,
                                        REDUCTION_TYPE>,
                SYMBOL_TYPE
                        extends RCFG_Symbol
                                <TERMINAL_TYPE,
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
        extends CFG_Terminal
        <TERMINAL_TYPE,
                PRODUCTION_TYPE,
                SYMBOL_TYPE,
                ENUM_TERMINAL_ID,
                TERMINAL_VALUE_TYPE,
                ENUM_PRODUCTION_ID>
        implements RCFG_Symbol
        <TERMINAL_TYPE,
                PRODUCTION_TYPE,
                SYMBOL_TYPE,
                ENUM_TERMINAL_ID,
                TERMINAL_VALUE_TYPE,
                ENUM_PRODUCTION_ID,
                REDUCTION_TYPE>
{
}
