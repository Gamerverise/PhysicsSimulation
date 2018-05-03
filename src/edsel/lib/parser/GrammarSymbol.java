package edsel.lib.parser;

public class GrammarSymbol<PRODUCTION, TERMINALS extends GrammarTerminal<TERMINALS>> {

    public EXPANSIONS expansions;
    public TERMINALS terminals;
}
