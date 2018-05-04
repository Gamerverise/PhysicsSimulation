package edsel.lib.cfg_parser.reductions;

import java.util.LinkedList;

public abstract class CFG_DetReduction
        <ENUM_TERMINAL_ID extends Enum<ENUM_TERMINAL_ID>,
                TERMINAL_VALUE_TYPE,
                ENUM_PRODUCTION_ID extends Enum<ENUM_PRODUCTION_ID>>
{
    public abstract CFG_DetReduction<ENUM_TERMINAL_ID, TERMINAL_VALUE_TYPE, ENUM_PRODUCTION_ID>
    reduce(LinkedList
                   <CFG_DetReduction<ENUM_TERMINAL_ID,
                                                      TERMINAL_VALUE_TYPE,
                                                      ENUM_PRODUCTION_ID>>         sub_reductions);
}
