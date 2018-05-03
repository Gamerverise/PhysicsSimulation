package edsel.lib.parser.grammar;

import java.util.List;

public abstract class GrammarProductionSet
        <ENUM_PRODUCTION_ID extends Enum<ENUM_PRODUCTION_ID>,
                PRODUCTION_SET extends GrammarProduction<ENUM_PRODUCTION_ID>,

                TERMINAL_SET extends GrammarTerminalSet<ENUM_TERMINAL_ID, TERMINAL_SET>,
                GRAMMAR_SYMBOL extends GrammarSymbol<TERMINAL_SET, PRODUCTION_SET>,
{
    List<GRAMMAR_PRODUCTION> productions;
}
