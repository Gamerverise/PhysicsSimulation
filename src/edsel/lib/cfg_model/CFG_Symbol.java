package edsel.lib.cfg_model;

public class CFG_Symbol<ENUM_SYMBOL_ID extends Enum<ENUM_SYMBOL_ID>>
{
    public ENUM_SYMBOL_ID id;

    public CFG_Symbol() {}

    public CFG_Symbol(ENUM_SYMBOL_ID id) {
        this.id = id;
    }

    public void init(ENUM_SYMBOL_ID id) {
        this.id = id;
    }
}
