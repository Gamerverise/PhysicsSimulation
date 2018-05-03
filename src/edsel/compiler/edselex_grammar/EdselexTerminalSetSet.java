package edsel.compiler.edselex_grammar;

import edsel.lib.parser.grammar.GrammarTerminalSet;

public class EdselexTerminalSetSet extends GrammarTerminalSet<EdselexTerminal> {

    public static EdselexTerminalSetSet EDSELEX_TERMINALS = new EdselexTerminalSetSet();

    // ======================================================================================

    public EdselexTerminal get_eof() {
        return EOF;
    }

    // ======================================================================================

    public EdselexTerminal EOF = new EdselexTerminal(null);

    public EdselexTerminal ESCAPE_CHAR                    = new EdselexTerminal("\\");
    public EdselexTerminal LITERAL_CHAR                    = new EdselexTerminal("\\");

    public EdselexTerminal VERSION_REQUIRED          = new EdselexTerminal("VERSION_REQUIRED");
    public EdselexTerminal THIS_VERSION              = new EdselexTerminal("THIS_VERSION");
    public EdselexTerminal VERSION_SPECIFIER         = new EdselexTerminal("[^ \\tn]+");

    public EdselexTerminal LEX_KEYWORD               = new EdselexTerminal("LEX");
    public EdselexTerminal TOKEN_IDENTIFIER          = new EdselexTerminal("[_A-Za-z0-9]+");

    public EdselexTerminal GRAMMAR_KEYWORD           = new EdselexTerminal("GRAMMAR");
    public EdselexTerminal GRAMMAR_LHS               = new EdselexTerminal("[^ \\t\\n]+");
    public EdselexTerminal PRODUCTION_OP             = new EdselexTerminal("->");
    public EdselexTerminal TRANSLATION_OP            = new EdselexTerminal(":");

    public EdselexTerminal EXPANSION_LITERAL         = new EdselexTerminal("[^ \\t\\n]+");
    public EdselexTerminal EXPANSION_IDENTIFIER      = new EdselexTerminal("<[_A-Za-z0-9]+>");

    public EdselexTerminal TRANSLATION_LITERAL       = new EdselexTerminal("[^ \\t\\n]+");
    public EdselexTerminal INDEX_REF                 = new EdselexTerminal(":0|([1-9][0-9]*)");
    public EdselexTerminal NAMED_REF                 = new EdselexTerminal("[^ \\t\\n]+");

    public EdselexTerminal CODE_KEYWORD              = new EdselexTerminal("CODE");
    public EdselexTerminal CODE                      = new EdselexTerminal("[^ \\t\\n]*");
    public EdselexTerminal FORCE_REDUCTION_OP        = new EdselexTerminal("#");
}
