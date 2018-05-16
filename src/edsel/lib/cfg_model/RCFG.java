package edsel.lib.cfg_model;

import edsel.lib.io.ParseNodeBuffer;

public class RCFG
        <ENUM_PRODUCTION_ID extends Enum<ENUM_PRODUCTION_ID>,
                PRODUCTION_TYPE extends
                        RCFG_Production
                                <ENUM_PRODUCTION_ID>>
{
    public PRODUCTION_TYPE          start_production;
    public PRODUCTION_TYPE[]        productions;

    public RCFG(
            PRODUCTION_TYPE         start_production,
            PRODUCTION_TYPE[]       productions)
    {
        this.start_production = start_production;
        this.productions = productions;
    }

    public ENUM_PRODUCTION_ID get_production_id(
            ParseNodeBuffer.ParseNodeBufferString
                    production_name)
    {
        for(PRODUCTION_TYPE production : productions)
            if (production_name.get_string() == production.name)
                return production.id;

        return null;
    }
}
