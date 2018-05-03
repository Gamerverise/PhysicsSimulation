package edsel.versions._1_primordial.parser.edselex_grammar;

import edsel.lib.cfg_model.CFG_Symbol;

public class EdselexSymbolSet extends CFG_Symbol<EdselexProduction, EdselexTerminal> {

    public static EdselexSymbolSet EDSELEX_GRAMMAR_SYMBOL;

    {
        EDSELEX_GRAMMAR_SYMBOL.expansions = EDSELEX_GRAMMAR_EXPANSIONS;
        EDSELEX_GRAMMAR_SYMBOL.terminals = EDSELEX_GRAMMAR_TERMINALS;
    }
}
