package edsel.versions._1_primordial.parser.old;

import edsel.lib.cfg_model.CFG_TerminalSet;

public class EdselexGrammarTerminalSet extends CFG_TerminalSet<EdselexGrammarTerminalSet> {

    String edselex;

    public EdselexGrammarTerminalSet get_eof() {
        return eof;
    }

    public static EdselexGrammarTerminalSet eof = new EdselexGrammarTerminalSet("");

    public static EdselexGrammarTerminalSet ESCAPE                    = new EdselexGrammarTerminalSet("\\");
    public static EdselexGrammarTerminalSet SEPARATOR                 = new EdselexGrammarTerminalSet("[ \\t\\n]+");
    public static EdselexGrammarTerminalSet QUOTE                     = new EdselexGrammarTerminalSet("\"");

    public static EdselexGrammarTerminalSet BLOCK_SEPARATOR           = new EdselexGrammarTerminalSet("[ \\t\\n]+");

    public static EdselexGrammarTerminalSet VERSION_REQUIRED          = new EdselexGrammarTerminalSet("VERSION_REQUIRED");
    public static EdselexGrammarTerminalSet THIS_VERSION              = new EdselexGrammarTerminalSet("THIS_VERSION");
    public static EdselexGrammarTerminalSet VERSION_SPECIFIER         = new EdselexGrammarTerminalSet("[^ \\tn]+");

    public static EdselexGrammarTerminalSet LEX_KEYWORD               = new EdselexGrammarTerminalSet("LEX");
    public static EdselexGrammarTerminalSet TOKEN_IDENTIFIER          = new EdselexGrammarTerminalSet("[_A-Za-z0-9]+");

    public static EdselexGrammarTerminalSet GRAMMAR_KEYWORD           = new EdselexGrammarTerminalSet("GRAMMAR");
    public static EdselexGrammarTerminalSet GRAMMAR_LHS               = new EdselexGrammarTerminalSet("[^ \\t\\n]+");
    public static EdselexGrammarTerminalSet PRODUCTION_OP             = new EdselexGrammarTerminalSet("->");
    public static EdselexGrammarTerminalSet TRANSLATION_OP            = new EdselexGrammarTerminalSet(":");

    public static EdselexGrammarTerminalSet EXPANSION_LITERAL         = new EdselexGrammarTerminalSet("[^ \\t\\n]+");
    public static EdselexGrammarTerminalSet EXPANSION_IDENTIFIER      = new EdselexGrammarTerminalSet("<[_A-Za-z0-9]+>");

    public static EdselexGrammarTerminalSet TRANSLATION_LITERAL       = new EdselexGrammarTerminalSet("[^ \\t\\n]+");
    public static EdselexGrammarTerminalSet INDEX_REF                 = new EdselexGrammarTerminalSet(":0|([1-9][0-9]*)");
    public static EdselexGrammarTerminalSet NAMED_REF                 = new EdselexGrammarTerminalSet("[^ \\t\\n]+");

    public static EdselexGrammarTerminalSet CODE_KEYWORD              = new EdselexGrammarTerminalSet("CODE");
    public static EdselexGrammarTerminalSet CODE                      = new EdselexGrammarTerminalSet("[^ \\t\\n]*");
    public static EdselexGrammarTerminalSet FORCE_REDUCTION_OP        = new EdselexGrammarTerminalSet("#");

    public EdselexGrammarTerminalSet(String edselex) {
        this.edselex = edselex;
    }
}
