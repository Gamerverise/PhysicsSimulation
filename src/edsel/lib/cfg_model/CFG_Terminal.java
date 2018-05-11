package edsel.lib.cfg_model;

public class
CFG_Terminal
        <ENUM_TERMINAL_ID extends Enum<ENUM_TERMINAL_ID>,
                TERMINAL_VALUE_TYPE>
        implements
        CFG_Symbol
{
    public ENUM_TERMINAL_ID id;
    public TERMINAL_VALUE_TYPE value;
}
