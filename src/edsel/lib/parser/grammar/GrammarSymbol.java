package edsel.lib.parser.grammar;

public class GrammarSymbol
        <ENUM_TERMINAL_ID extends Enum<ENUM_TERMINAL_ID>,
                TERMINAL_SET extends GrammarTerminalSet<ENUM_TERMINAL_ID, TERMINAL_SET>,
                PRODUCTION_SET>
{
    public TERMINAL_SET terminals;
    public PRODUCTION_SET productions;
}
