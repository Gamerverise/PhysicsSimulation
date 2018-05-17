package edsel.lib.cfg_parser.parsing_restriction;

import edsel.lib.cfg_parser.RCFG_Parser.SymbolBuffer.SymbolBufferString;

public class ProductionRestriction extends ParsingRestriction
{

    public SymbolBufferString production_name;

    public ProductionRestriction(
            SymbolBufferString production_name,
            RestrictionMode mode)
    {
        super(mode);
        this.production_name = production_name;
    }
}
