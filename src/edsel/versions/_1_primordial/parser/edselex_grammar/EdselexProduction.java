package edsel.versions._1_primordial.parser.edselex_grammar;

public class EdselexProduction extends EdselexSymbol {

    EdselexSymbolSet[] production;

    public EdselexProduction(EdselexSymbolSet... production) {
        this.production = production;
    }
}
