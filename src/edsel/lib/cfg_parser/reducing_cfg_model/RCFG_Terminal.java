package edsel.lib.cfg_parser.reducing_cfg_model;

import edsel.lib.cfg_model.CFG_Terminal;

public abstract class RCFG_Terminal
        <ENUM_TERMINAL_ID extends Enum<ENUM_TERMINAL_ID>,
                VALUE_TYPE,
                ENUM_PRODUCTION_ID extends Enum<ENUM_PRODUCTION_ID>>
        extends CFG_Terminal
        <ENUM_TERMINAL_ID,
                VALUE_TYPE,
                ENUM_PRODUCTION_ID>
        implements RCFG_Symbol
        <ENUM_TERMINAL_ID,
                VALUE_TYPE,
                ENUM_PRODUCTION_ID>
{
    public ENUM_TERMINAL_ID id;
    public VALUE_TYPE value;
}
