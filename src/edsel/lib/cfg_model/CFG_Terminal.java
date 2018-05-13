package edsel.lib.cfg_model;

public class
CFG_Terminal
        <ENUM_TERMINAL_ID extends Enum<ENUM_TERMINAL_ID>>
        implements CFG_Symbol
{
    public ENUM_TERMINAL_ID id;
    public String pattern;

    public CFG_Terminal(ENUM_TERMINAL_ID id, String pattern) {
        this.id = id;
        this.pattern = pattern;
    }
}
