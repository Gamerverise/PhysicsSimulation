package edsel.versions._1_primordial.parser.edselex_grammar;

public class EdselexTerminal extends EdselexSymbol {

    TerminalType type;
    String edselex;

    public EdselexTerminal(String edselex) {
        this.edselex = edselex;
    }

    public static EdselexTerminal ESCAPE_CHAR                    = new EdselexTerminal("\\");
    public EdselexTerminal LITERAL_CHAR                    = new EdselexTerminal("\\");

}
