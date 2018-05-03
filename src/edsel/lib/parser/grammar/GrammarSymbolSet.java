package edsel.lib.parser.grammar;

public class GrammarSymbolSet
        <TERMINAL_ID extends Enum<TERMINAL_ID>,
                TERMINAL_VALUE,
                TERMINAL_SET extends
                        GrammarTerminalSet<TERMINAL_SET>,

                PRODUCTION_ID,
                PRODUCTION_SET,

                GRAMMAR_SYMBOL extends GrammarSymbol<PRODUCTION_SET, TERMINAL_SET>,
                GRAMMAR_PRODUCTION extends GrammarProduction<ENUM_PRODUCTION_ID, PRODUCITON_SET, TERMINAL_SET, GRAMMAR_SYMBOL>,



    public class GrammarProduction
            <ENUM_ID, PRODUCTION_SET, TERMINAL_SET extends GrammarTerminalSet<TERMINAL_SET>,
                    GRAMMAR_SYMBOL extends GrammarSymbol<PRODUCTION_SET, TERMINAL_SET>>

    public abstract class GrammarProductionSet
            <ENUM_ID,
                    PRODUCTION_SET,
                    TERMINAL_SET extends GrammarTerminalSet<TERMINAL_SET>,
                    GRAMMAR_SYMBOL extends GrammarSymbol<PRODUCTION_SET, TERMINAL_SET>,
                    GRAMMAR_PRODUCTION extends edsel.lib.parser.grammar.GrammarProduction<ENUM_ID, PRODUCTION_SET, TERMINAL_SET, GRAMMAR_SYMBOL>>
{
    TERMINAL_SET terminals;
    PRODUCTION_SET productions;
}
