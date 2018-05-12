package edsel.lib.cfg_parser;

import edsel.lib.cfg_model.RCFG_Production;

public class ProductionEntry
        <ENUM_TERMINAL_ID extends Enum<ENUM_TERMINAL_ID>,
                ENUM_PRODUCTION_ID extends Enum<ENUM_PRODUCTION_ID>,
                REDUCTION_TYPE>
        extends
        ParseStateEntry<ENUM_TERMINAL_ID, ENUM_PRODUCTION_ID, REDUCTION_TYPE>
{
    public RCFG_Production<ENUM_PRODUCTION_ID, REDUCTION_TYPE>
            production;

    public ProductionEntry(
            ParseStateEntry<ENUM_TERMINAL_ID, ENUM_PRODUCTION_ID, REDUCTION_TYPE>
                    prev_state,
            RCFG_Production<ENUM_PRODUCTION_ID, REDUCTION_TYPE>
                    parent_production)
    {
        super(prev_state);
        this.production = parent_production;
    }
}
