package edsel.lib.cfg_model;

import java.util.LinkedList;

public class CFG_Reduction
        <ENUM_TERMINAL_ID extends Enum<ENUM_TERMINAL_ID>,
                TERMINAL_VALUE_TYPE,
                ENUM_PRODUCTION_ID extends Enum<ENUM_PRODUCTION_ID>,
                REDUCTION_TYPE extends CFG_Reduction
                        <ENUM_TERMINAL_ID, TERMINAL_VALUE_TYPE, ENUM_PRODUCTION_ID, REDUCTION_TYPE>>
{
    public CFG_Reduction
            <ENUM_TERMINAL_ID,
                    TERMINAL_VALUE_TYPE,
                    ENUM_PRODUCTION_ID,
                    REDUCTION_TYPE>
    reduce(LinkedList<REDUCTION_TYPE> sub_parse) {
        return;
    }
}
