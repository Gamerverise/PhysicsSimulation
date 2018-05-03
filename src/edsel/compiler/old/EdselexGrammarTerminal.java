package edsel.compiler.old;

import edsel.lib.parser.GrammarTerminal;

public class EdselexGrammarTerminal extends GrammarTerminal<EdselexGrammarTerminal> {

    String edselex;

    public EdselexGrammarTerminal get_eof() {
        return eof;
    }

    public static EdselexGrammarTerminal eof = new EdselexGrammarTerminal("");

    public static EdselexGrammarTerminal ESCAPE                    = new EdselexGrammarTerminal("\\");
    public static EdselexGrammarTerminal SEPARATOR                 = new EdselexGrammarTerminal("[ \\t\\n]+");
    public static EdselexGrammarTerminal QUOTE                     = new EdselexGrammarTerminal("\"");

    public static EdselexGrammarTerminal BLOCK_SEPARATOR           = new EdselexGrammarTerminal("[ \\t\\n]+");

    public static EdselexGrammarTerminal VERSION_REQUIRED          = new EdselexGrammarTerminal("VERSION_REQUIRED");
    public static EdselexGrammarTerminal THIS_VERSION              = new EdselexGrammarTerminal("THIS_VERSION");
    public static EdselexGrammarTerminal VERSION_SPECIFIER         = new EdselexGrammarTerminal("[^ \\tn]+");

    public static EdselexGrammarTerminal LEX_KEYWORD               = new EdselexGrammarTerminal("LEX");
    public static EdselexGrammarTerminal TOKEN_IDENTIFIER          = new EdselexGrammarTerminal("[_A-Za-z0-9]+");

    public static EdselexGrammarTerminal GRAMMAR_KEYWORD           = new EdselexGrammarTerminal("GRAMMAR");
    public static EdselexGrammarTerminal GRAMMAR_LHS               = new EdselexGrammarTerminal("[^ \\t\\n]+");
    public static EdselexGrammarTerminal PRODUCTION_OP             = new EdselexGrammarTerminal("->");
    public static EdselexGrammarTerminal TRANSLATION_OP            = new EdselexGrammarTerminal(":");

    public static EdselexGrammarTerminal EXPANSION_LITERAL         = new EdselexGrammarTerminal("[^ \\t\\n]+");
    public static EdselexGrammarTerminal EXPANSION_IDENTIFIER      = new EdselexGrammarTerminal("<[_A-Za-z0-9]+>");

    public static EdselexGrammarTerminal TRANSLATION_LITERAL       = new EdselexGrammarTerminal("[^ \\t\\n]+");
    public static EdselexGrammarTerminal INDEX_REF                 = new EdselexGrammarTerminal(":0|([1-9][0-9]*)");
    public static EdselexGrammarTerminal NAMED_REF                 = new EdselexGrammarTerminal("[^ \\t\\n]+");

    public static EdselexGrammarTerminal CODE_KEYWORD              = new EdselexGrammarTerminal("CODE");
    public static EdselexGrammarTerminal CODE                      = new EdselexGrammarTerminal("[^ \\t\\n]*");
    public static EdselexGrammarTerminal FORCE_REDUCTION_OP        = new EdselexGrammarTerminal("#");

    public EdselexGrammarTerminal(String edselex) {
        this.edselex = edselex;
    }
}
