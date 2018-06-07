package edsel.lib.cfg_parser;

public class ParsingState
        <SYMBOL_BUFFER_TYPE extends CFG_Parser.SymbolBuffer<SYMBOL_BUFFER_TYPE>>
{
    public SYMBOL_BUFFER_TYPE input;

    public ParsingState(SYMBOL_BUFFER_TYPE input) {
        this.input = input;
    }
}
