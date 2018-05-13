package edsel.lib.cfg_model;

public class
CFG_Production
        <ENUM_PRODUCTION_ID extends Enum<ENUM_PRODUCTION_ID>>
        implements
        CFG_Symbol
{
    public ENUM_PRODUCTION_ID id;

    public CFG_Symbol[][] rhs;

    public CFG_Production(ENUM_PRODUCTION_ID id, CFG_Symbol[]... rhs) {
        this.id = id;
        this.rhs = rhs;
    }
}
