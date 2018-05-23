package edsel.lib.cfg_parser.parsing_restriction;

import edsel.lib.cfg_model.CFG_Production;

public class ProductionRestriction<ENUM_PRODUCTION_ID extends Enum<ENUM_PRODUCTION_ID>>
        implements ParsingRestriction
{
    public enum RestrictionMode {PREFIX_MODE, EXACT_MODE}

    public CFG_Production<ENUM_PRODUCTION_ID> production;
    public RestrictionMode mode;

    public ProductionRestriction(RestrictionMode mode) {
        this.mode = mode;
    }

    public ProductionRestriction(
            CFG_Production<ENUM_PRODUCTION_ID> production, RestrictionMode mode)
    {
        this.production = production;
        this.mode = mode;
    }
}
