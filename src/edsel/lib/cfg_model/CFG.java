package edsel.lib.cfg_model;

public class CFG
        <ENUM_PRODUCTION_ID extends Enum<ENUM_PRODUCTION_ID>,
                ENUM_TERMINAL_ID extends Enum<ENUM_TERMINAL_ID>,
                TOKEN_VALUE_TYPE>
{
    public CFG_Production<ENUM_PRODUCTION_ID> start_production;
    public CFG_Terminal<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE> eof_terminal;

    public CFG(
            CFG_Production<ENUM_PRODUCTION_ID> start_production,
            CFG_Terminal<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE> eof_terminal)
    {
        this.start_production = start_production;
        this.eof_terminal = eof_terminal;
    }
}
