package edsel.lib.cfg_parser;

import edsel.lib.cfg_model.RCFG_Production;

public class ProductionBranchEntry
        <ENUM_PRODUCTION_ID extends Enum<ENUM_PRODUCTION_ID>,
                REDUCTION_TYPE>
        extends
        ParseStateEntry<ENUM_PRODUCTION_ID, ENUM_PRODUCTION_ID, REDUCTION_TYPE>
{
    public RCFG_Production<ENUM_PRODUCTION_ID, REDUCTION_TYPE> production;
    public int cur_alternative;
    public int cur_alternative_index;

    public ProductionBranchEntry(
            ParseStateEntry<ENUM_PRODUCTION_ID, ENUM_PRODUCTION_ID, REDUCTION_TYPE>
                    prev_state,
            RCFG_Production<ENUM_PRODUCTION_ID, REDUCTION_TYPE> parent_production,
            int cur_alternative,
            int cur_alternative_index)
    {
        super(prev_state);
        this.production = parent_production;
        this.cur_alternative = cur_alternative;
        this.cur_alternative_index = cur_alternative_index;
    }
}
