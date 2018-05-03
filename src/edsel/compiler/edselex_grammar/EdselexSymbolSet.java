package edsel.compiler.edselex_grammar;

import edsel.lib.cfg.data_structure.CFG_Symbol;

public class EdselexSymbolSet extends CFG_Symbol<EdselexProduction, EdselexTerminal> {

    public static EdselexSymbolSet EDSELEX_GRAMMAR_SYMBOL;

    {
        EDSELEX_GRAMMAR_SYMBOL.expansions = EDSELEX_GRAMMAR_EXPANSIONS;
        EDSELEX_GRAMMAR_SYMBOL.terminals = EDSELEX_GRAMMAR_TERMINALS;
    }
}
