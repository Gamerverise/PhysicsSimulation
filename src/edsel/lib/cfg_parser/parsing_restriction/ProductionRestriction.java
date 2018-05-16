package edsel.lib.cfg_parser.parsing_restriction;

import edsel.lib.cfg_model.RCFG_Production;

public class ProductionRestriction
        <ENUM_PRODUCTION_ID extends Enum<ENUM_PRODUCTION_ID>>
        extends
        ParsingRestriction
{

    public RCFG_Production<ENUM_PRODUCTION_ID> production;

    public ProductionRestriction(
            RCFG_Production<ENUM_PRODUCTION_ID> production,
            RestrictionMode mode)
    {
        super(mode);
        this.production = production;
    }
}
