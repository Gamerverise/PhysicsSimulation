package edsel.compiler.edselex_grammar;

import edsel.lib.parser.grammar.GrammarSymbol;

public class EdselexSymbolSet extends GrammarSymbol<EdselexProduction, EdselexTerminal> {

    public static EdselexSymbolSet EDSELEX_GRAMMAR_SYMBOL;

    {
        EDSELEX_GRAMMAR_SYMBOL.expansions = EDSELEX_GRAMMAR_EXPANSIONS;
        EDSELEX_GRAMMAR_SYMBOL.terminals = EDSELEX_GRAMMAR_TERMINALS;
    }
}
