package edsel.lib.cfg_parser.reducing_cfg_model;

import edsel.lib.cfg_model.CFG_Production;

public abstract
class RCFG_Production
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
        extends CFG_Production
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
    @SafeVarargs
    public RCFG_Production(
            ENUM_PRODUCTION_ID
                    id,
            RCFG_Symbol
                    <TERMINAL_TYPE,
                            PRODUCTION_TYPE,
                            SYMBOL_TYPE,
                            ENUM_TERMINAL_ID,
                            TERMINAL_VALUE_TYPE,
                            ENUM_PRODUCTION_ID,
                            REDUCTION_TYPE>...
                    rhs)
    {
        super(id, rhs);
    }

    public abstract REDUCTION_TYPE reduce(REDUCTION_TYPE[] sub_reductions);
}
