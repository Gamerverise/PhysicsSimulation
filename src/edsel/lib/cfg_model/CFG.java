package edsel.lib.cfg_model;

import java.util.LinkedList;

public class CFG
        <TERMINAL_TYPE
                extends CFG_Terminal
                        <TERMINAL_TYPE,
                                PRODUCTION_TYPE,
                                SYMBOL_TYPE,
                                ENUM_TERMINAL_ID,
                                TERMINAL_VALUE_TYPE,
                                ENUM_PRODUCTION_ID>,
                PRODUCTION_TYPE
                        extends CFG_Production
                                <TERMINAL_TYPE,
                                        PRODUCTION_TYPE,
                                        SYMBOL_TYPE,
                                        ENUM_TERMINAL_ID,
                                        TERMINAL_VALUE_TYPE,
                                        ENUM_PRODUCTION_ID>,
                SYMBOL_TYPE
                        extends CFG_Symbol
                                <TERMINAL_TYPE,
                                        PRODUCTION_TYPE,
                                        SYMBOL_TYPE,
                                        ENUM_TERMINAL_ID,
                                        TERMINAL_VALUE_TYPE,
                                        ENUM_PRODUCTION_ID>,
                ENUM_TERMINAL_ID extends Enum<ENUM_TERMINAL_ID>,
                TERMINAL_VALUE_TYPE,
                ENUM_PRODUCTION_ID extends Enum<ENUM_PRODUCTION_ID>>
{
    public static class InvalidCFG_Input extends Exception {}

    // =========================================================================================

    public LinkedList<TERMINAL_TYPE> terminals;

    public TERMINAL_TYPE eof;

    public LinkedList<PRODUCTION_TYPE> productions;

    public PRODUCTION_TYPE root_production;

    // =========================================================================================

    public CFG(
            LinkedList<TERMINAL_TYPE> terminals,
            TERMINAL_TYPE eof,
            LinkedList<PRODUCTION_TYPE> productions,
            PRODUCTION_TYPE root_production)
    {
        this.terminals = terminals;
        this.eof = eof;
        this.productions = productions;
        this.root_production = root_production;
    }
}
