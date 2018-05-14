package edsel.lib.cfg_model;

public class
CFG_Terminal<ENUM_TERMINAL_ID extends Enum<ENUM_TERMINAL_ID>>
        extends CFG_Symbol<ENUM_TERMINAL_ID>
{
    public String pattern;

    public CFG_Terminal(ENUM_TERMINAL_ID id, String pattern) {
        super(id);
        this.id = id;
        this.pattern = pattern;
    }
}
