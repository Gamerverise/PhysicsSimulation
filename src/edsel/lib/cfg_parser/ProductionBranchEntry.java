package edsel.lib.cfg_parser;

import edsel.lib.cfg_model.RCFG_Production;

public class ProductionBranchEntry
        <PRODUCTION_TYPE extends RCFG_Production<ENUM_PRODUCTION_ID, REDUCTION_TYPE>,
                ENUM_PRODUCTION_ID extends Enum<ENUM_PRODUCTION_ID>,
                REDUCTION_TYPE>
        extends
        ParseStateEntry
{
    public ProductionBranchEntry(
            ParseStateEntry prev_state,
            PRODUCTION_TYPE parent_production,
            int cur_alternative,
            int cur_alternative_index)
    {
        super(prev_state);
        this.production = parent_production;
        this.cur_alternative = cur_alternative;
        this.cur_alternative_index = cur_alternative_index;
    }

    public PRODUCTION_TYPE production;
    public int cur_alternative;
    public int cur_alternative_index;
}
