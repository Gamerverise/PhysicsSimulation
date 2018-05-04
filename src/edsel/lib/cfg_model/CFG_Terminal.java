package edsel.lib.cfg_model;

public class CFG_Terminal
        <ENUM_TERMINAL_ID extends Enum<ENUM_TERMINAL_ID>,
                VALUE_TYPE,
                ENUM_PRODUCTION_ID extends Enum<ENUM_PRODUCTION_ID>>
    extends CFG_Symbol<ENUM_TERMINAL_ID, VALUE_TYPE, ENUM_PRODUCTION_ID>
{
    public ENUM_TERMINAL_ID id;
    public VALUE_TYPE value;
}
