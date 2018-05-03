package edsel.lib.cfg.data_structure;

public class CFG_SymbolSet
        <ENUM_TERMINAL_ID extends Enum<ENUM_TERMINAL_ID>,
                TERMINAL_VALUE_TYPE,
                ENUM_PRODUCTION_ID extends Enum<ENUM_PRODUCTION_ID>,
                REDUCTION_TYPE>
{
    CFG_TerminalSet<ENUM_TERMINAL_ID,
                        TERMINAL_VALUE_TYPE,
                        ENUM_PRODUCTION_ID,
                        REDUCTION_TYPE>
            terminals;

    CFG_ProductionSet<ENUM_TERMINAL_ID,
                        TERMINAL_VALUE_TYPE,
                        ENUM_PRODUCTION_ID,
                        REDUCTION_TYPE>
            productions;
}
