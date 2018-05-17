package edsel.lib.cfg_model;

public class
CFG_Production
        <ENUM_PRODUCTION_ID extends Enum<ENUM_PRODUCTION_ID>,
                SYMBOL_TYPE extends CFG_Symbol>
        implements CFG_Symbol
{
    public ENUM_PRODUCTION_ID id;
    public SYMBOL_TYPE[][] rhs;

    public CFG_Production() {}

    public void init(ENUM_PRODUCTION_ID id, SYMBOL_TYPE[]... rhs) {
        this.id = id;
        this.rhs = rhs;
    }
}
