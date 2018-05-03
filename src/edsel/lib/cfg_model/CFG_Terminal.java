package edsel.lib.cfg_model;

public class CFG_Terminal
        <ENUM_TERMINAL_ID extends Enum<ENUM_TERMINAL_ID>,
                VALUE_TYPE,
                ENUM_PRODUCTION_ID extends Enum<ENUM_PRODUCTION_ID>,
                REDUCTION_TYPE extends CFG_Reduction
                        <ENUM_TERMINAL_ID, VALUE_TYPE, ENUM_PRODUCTION_ID, REDUCTION_TYPE>>
    extends CFG_Symbol<ENUM_TERMINAL_ID, VALUE_TYPE, ENUM_PRODUCTION_ID, REDUCTION_TYPE>
{
    ENUM_TERMINAL_ID id;
    VALUE_TYPE value;
}
