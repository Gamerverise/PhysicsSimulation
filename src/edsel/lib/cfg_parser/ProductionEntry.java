package edsel.lib.cfg_parser;

import edsel.lib.cfg_model.RCFG_Production;

public class ProductionEntry
        <ENUM_TERMINAL_ID extends Enum<ENUM_TERMINAL_ID>,
                TERMINAL_VALUE_TYPE,
                ENUM_PRODUCTION_ID extends Enum<ENUM_PRODUCTION_ID>,
                REDUCTION_TYPE>
        implements
        ParseStateEntry
{
    public RCFG_Production<ENUM_TERMINAL_ID, TERMINAL_VALUE_TYPE, ENUM_PRODUCTION_ID, REDUCTION_TYPE>
            production;

    public ProductionEntry(
            RCFG_Production<ENUM_TERMINAL_ID, TERMINAL_VALUE_TYPE, ENUM_PRODUCTION_ID, REDUCTION_TYPE>
                    lhs_production)
    {
        this.production = lhs_production;
    }
}
