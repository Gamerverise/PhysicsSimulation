package edsel.lib.cfg_model;

public class RCFG
        <ENUM_PRODUCTION_ID extends Enum<ENUM_PRODUCTION_ID>,
                ENUM_TERMINAL_ID extends Enum<ENUM_TERMINAL_ID>,
                TOKEN_VALUE_TYPE>
{
    public RCFG_Production<ENUM_PRODUCTION_ID> start_production;
    public RCFG_Terminal<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE> restrict;
    public RCFG_Terminal<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE> unrestrict;
    public RCFG_Terminal<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE> gate;

    public RCFG(
            RCFG_Production<ENUM_PRODUCTION_ID> start_production,
            RCFG_Terminal<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE> restrict,
            RCFG_Terminal<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE> unrestrict,
            RCFG_Terminal<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE> gate)
    {
        this.start_production = start_production;
        this.restrict = restrict;
        this.unrestrict = unrestrict;
        this.gate = gate;
    }
}
