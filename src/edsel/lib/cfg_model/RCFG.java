package edsel.lib.cfg_model;

import edsel.lib.io.Token;

public class RCFG
        <ENUM_PRODUCTION_ID extends Enum<ENUM_PRODUCTION_ID>,
                PRODUCTION_TYPE extends
                        RCFG_Production
                                <ENUM_PRODUCTION_ID,
                                        ENUM_TERMINAL_ID,
                                        TOKEN_VALUE_TYPE,
                                        TOKEN_TYPE>,
                ENUM_TERMINAL_ID extends Enum<ENUM_TERMINAL_ID>,
                TOKEN_VALUE_TYPE,
                TOKEN_TYPE extends
                        Token<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE, TOKEN_TYPE>>
{
    public PRODUCTION_TYPE                                                  start_production;
    public RCFG_Terminal<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE, TOKEN_TYPE>    restrict;
    public RCFG_Terminal<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE, TOKEN_TYPE>    unrestrict;
    public RCFG_Terminal<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE, TOKEN_TYPE>    gate;

    public RCFG(
            PRODUCTION_TYPE start_production,
            RCFG_Terminal<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE, TOKEN_TYPE>   restrict,
            RCFG_Terminal<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE, TOKEN_TYPE>   unrestrict,
            RCFG_Terminal<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE, TOKEN_TYPE>   gate)
    {
        this.start_production = start_production;
        this.restrict = restrict;
        this.unrestrict = unrestrict;
        this.gate = gate;
    }
}
