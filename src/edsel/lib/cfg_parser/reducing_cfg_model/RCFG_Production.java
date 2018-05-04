package edsel.lib.cfg_parser.reducing_cfg_model;

import edsel.lib.cfg_model.CFG_Production;

public abstract
class RCFG_Production
        <ENUM_TERMINAL_ID extends Enum<ENUM_TERMINAL_ID>,
                TERMINAL_VALUE_TYPE,
                ENUM_PRODUCTION_ID extends Enum<ENUM_PRODUCTION_ID>>
        extends CFG_Production
        <ENUM_TERMINAL_ID,
                TERMINAL_VALUE_TYPE,
                ENUM_PRODUCTION_ID>
        implements RCFG_Symbol
        <ENUM_TERMINAL_ID,
                TERMINAL_VALUE_TYPE,
                ENUM_PRODUCTION_ID>
{

    public enum TransitionType {
        SHIFT,
        REDUCE,
        SHIFT_OR_REDUCE,
        NOT_APPLICABLE
    }
}
