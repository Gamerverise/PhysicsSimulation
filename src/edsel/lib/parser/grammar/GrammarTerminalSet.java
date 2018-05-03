package edsel.lib.parser.grammar;

public abstract class GrammarTerminalSet
        <ENUM_TERMINAL_ID extends Enum<ENUM_TERMINAL_ID>,
                SUBCLASS extends GrammarTerminalSet<ENUM_TERMINAL_ID, SUBCLASS>>
{
    public abstract SUBCLASS get_eof();
}
