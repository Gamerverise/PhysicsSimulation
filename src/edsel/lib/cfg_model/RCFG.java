package edsel.lib.cfg_model;

import edsel.lib.io.Token;

public class RCFG
        <ENUM_PRODUCTION_ID extends Enum<ENUM_PRODUCTION_ID>,
                PRODUCTION_TYPE extends
                        RCFG_Production
                                <ENUM_PRODUCTION_ID,
                                        ENUM_TERMINAL_ID,
                                        TOKEN_VALUE_TYPE>,
                ENUM_TERMINAL_ID extends Enum<ENUM_TERMINAL_ID>,
                TOKEN_VALUE_TYPE>
{
    public PRODUCTION_TYPE          start_production;
    public ENUM_TERMINAL_ID                restrict_id;
    public ENUM_TERMINAL_ID                unrestrict_id;
    public ENUM_TERMINAL_ID                gate_id;

    public RCFG(
            PRODUCTION_TYPE         start_production,
            ENUM_TERMINAL_ID        restrict_id,
            ENUM_TERMINAL_ID        unrestrict_id,
            ENUM_TERMINAL_ID        gate_id)
    {
        this.start_production = start_production;
        this.restrict_id = restrict_id;
        this.unrestrict_id = unrestrict_id;
        this.gate_id = gate_id;
    }
}
