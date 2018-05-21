package edsel.lib.cfg_parser.parsing_restriction;

import edsel.lib.cfg_parser.CFG_Parser.SymbolBufferSymbol;

public abstract class ParsingRestriction
        implements SymbolBufferSymbol
{
    public RestrictionMode mode;

    public ParsingRestriction(RestrictionMode mode) {
        this.mode = mode;
    }
}
