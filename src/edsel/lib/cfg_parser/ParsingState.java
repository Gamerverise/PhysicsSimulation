package edsel.lib.cfg_parser;

public class ParsingState
        <ENUM_PRODUCTION_ID extends Enum<ENUM_PRODUCTION_ID>,
                ENUM_TERMINAL_ID extends Enum<ENUM_TERMINAL_ID>,
                TOKEN_VALUE_TYPE,
                SYMBOL_BUFFER_TYPE extends
                        CFG_Parser
                                <ENUM_PRODUCTION_ID,
                                        ENUM_TERMINAL_ID,
                                        TOKEN_VALUE_TYPE,
                                        SYMBOL_BUFFER_TYPE>
                                .SymbolBuffer>
{
    public SYMBOL_BUFFER_TYPE input;

    public ParsingState(SYMBOL_BUFFER_TYPE input) {
        this.input = input;
    }
}
