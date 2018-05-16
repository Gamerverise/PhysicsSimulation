package edsel.lib.cfg_parser.parsing_restriction;

public abstract class ParsingRestriction {

    public RestrictionMode mode;

    public ParsingRestriction(RestrictionMode mode) {
        this.mode = mode;
    }
}
