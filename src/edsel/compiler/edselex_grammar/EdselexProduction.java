package edsel.compiler.edselex_grammar;

public class EdselexProduction extends EdselexSymbol {

    EdselexSymbolSet[] production;

    public EdselexProduction(EdselexSymbolSet... production) {
        this.production = production;
    }
}
