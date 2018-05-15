package edsel.lib.cfg_model;

import edsel.lib.io.TokenBuffer;

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
    public PRODUCTION_TYPE[]        productions;
    public ENUM_TERMINAL_ID         restrict_id;
    public ENUM_TERMINAL_ID         unrestrict_id;
    public ENUM_TERMINAL_ID         gate_id;

    public RCFG(
            PRODUCTION_TYPE         start_production,
            PRODUCTION_TYPE[]       productions,
            ENUM_TERMINAL_ID        restrict_id,
            ENUM_TERMINAL_ID        unrestrict_id,
            ENUM_TERMINAL_ID        gate_id)
    {
        this.start_production = start_production;
        this.restrict_id = restrict_id;
        this.unrestrict_id = unrestrict_id;
        this.gate_id = gate_id;
    }

    public ENUM_PRODUCTION_ID get_production_id(
            TokenBuffer<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE>.TokenBufferString
                    production_name)
    {
        for(PRODUCTION_TYPE production : productions)
            if (production_name.get_string() == production.name)
                return production.id;

        return null;
    }
}
