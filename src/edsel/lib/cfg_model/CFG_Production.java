package edsel.lib.cfg_model;

public class
CFG_Production<ENUM_PRODUCTION_ID extends Enum<ENUM_PRODUCTION_ID>>
        extends CFG_Symbol<ENUM_PRODUCTION_ID>
{
    public CFG_Symbol[][] rhs;

    public CFG_Production() {}

    public void init(ENUM_PRODUCTION_ID id, CFG_Symbol[]... rhs) {
        super.init(id);
        this.rhs = rhs;
    }
}
