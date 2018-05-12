package edsel.lib.cfg_model;

import java.util.LinkedList;

public class CFG
        <ENUM_TERMINAL_ID extends Enum<ENUM_TERMINAL_ID>,
                TERMINAL_VALUE_TYPE,
                ENUM_PRODUCTION_ID extends Enum<ENUM_PRODUCTION_ID>>
{
    public LinkedList<CFG_Terminal<ENUM_TERMINAL_ID, TERMINAL_VALUE_TYPE>> terminals;
    public CFG_Terminal<ENUM_TERMINAL_ID, TERMINAL_VALUE_TYPE> eof;
    public LinkedList<CFG_Production<ENUM_PRODUCTION_ID>> productions;
    public CFG_Production<ENUM_PRODUCTION_ID> root_production;

    // =========================================================================================

    public CFG(
            LinkedList<CFG_Terminal<ENUM_TERMINAL_ID, TERMINAL_VALUE_TYPE>> terminals,
            CFG_Terminal<ENUM_TERMINAL_ID, TERMINAL_VALUE_TYPE> eof,
            LinkedList<CFG_Production<ENUM_PRODUCTION_ID>> productions,
            CFG_Production<ENUM_PRODUCTION_ID> root_production)
    {
        this.terminals = terminals;
        this.eof = eof;
        this.productions = productions;
        this.root_production = root_production;
    }
}
