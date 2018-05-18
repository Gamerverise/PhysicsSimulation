package edsel.lib.cfg_parser.parsing_restriction;

public class ProductionRestriction
        <ENUM_PRODUCTION_ID extends Enum<ENUM_PRODUCTION_ID>>
        extends
        ParsingRestriction
{
    public CFG_Production<ENUM_PRODUCTION_ID> production;

    public ProductionRestriction(
            CFG_Production<ENUM_PRODUCTION_ID> production,
            RestrictionMode mode)
    {
        super(mode);
        this.production = production;
    }
}
