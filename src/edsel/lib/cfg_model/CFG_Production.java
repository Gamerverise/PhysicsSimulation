package edsel.lib.cfg_model;

import java.util.List;

public class CFG_Production
        <ENUM_TERMINAL_ID extends Enum<ENUM_TERMINAL_ID>,
                TERMINAL_VALUE_TYPE,
                ENUM_PRODUCTION_ID extends Enum<ENUM_PRODUCTION_ID>,
                REDUCTION_TYPE extends CFG_Reduction
                        <ENUM_TERMINAL_ID, TERMINAL_VALUE_TYPE, ENUM_PRODUCTION_ID, REDUCTION_TYPE>>
    extends CFG_Symbol<ENUM_TERMINAL_ID, TERMINAL_VALUE_TYPE, ENUM_PRODUCTION_ID, REDUCTION_TYPE>
{
    ENUM_PRODUCTION_ID id;

    List<CFG_Symbol<ENUM_TERMINAL_ID,
                        TERMINAL_VALUE_TYPE,
                        ENUM_PRODUCTION_ID,
                        REDUCTION_TYPE>>
            rhs;
}
