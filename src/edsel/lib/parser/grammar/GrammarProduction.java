package edsel.lib.parser.grammar;

import java.util.List;

public class GrammarProduction
        <TERMINAL_SET extends GrammarTerminalSet<ENUM_TERMINAL_ID, TERMINAL_SET>,
                PRODUCTION_SET,

                ENUM_TERMINAL_ID extends Enum<ENUM_TERMINAL_ID>,

                ENUM_PRODUCTION_ID extends Enum<ENUM_PRODUCTION_ID>,
                PRODUCTION,

                GRAMMAR_SYMBOL extends GrammarSymbol<ENUM_TERMINAL_ID, TERMINAL_SET, PRODUCTION_SET>
{
    ENUM_PRODUCTION_ID id;
    List<GRAMMAR_SYMBOL> rhs;
}
