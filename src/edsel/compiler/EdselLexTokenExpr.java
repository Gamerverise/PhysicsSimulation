package edsel.compiler;

public class EdselLexTokenExpr {

    public String ESCAPE                                  =       "\\"                  ;
    public String SEPARATOR                               =       "[ \\t\\n]+"          ;
    public String QUOTE                                   =       "\""                  ;

    public String BLOCK_SEPARATOR                         =       "[ \\t\\n]+"          ;

    public String VERSION_REQUIRED                        =       "VERSION_REQUIRED"    ;
    public String THIS_VERSION                            =       "THIS_VERSION"        ;
    public String VERSION_SPECIFIER                       =       "[^ \\tn]+"           ;

    public String LEX_KEYWORD                             =       "LEX"                 ;
    public String TOKEN_IDENTIFIER                        =       "[_A-Za-z0-9]+"       ;
//    public String MATCH_EXPRESSION                        =       "??????????"                    ;

    public String GRAMMAR_KEYWORD                         =       "GRAMMAR"             ;
    public String GRAMMAR_LHS                             =       "[^ \\t\\n]+"         ;
    public String PRODUCTION_OP                           =       "->"                  ;
    public String TRANSLATION_OP                          =       ":"                   ;

    public String EXPANSION_LITERAL                       =       "[^ \\t\\n]+"         ;
    public String EXPANSION_IDENTIFIER                    =       "<[_A-Za-z0-9]+>"     ;

    public String TRANSLATION_LITERAL                     =       "[^ \\t\\n]+"         ;
    public String INDEX_REF                               =       ":0|([1-9][0-9]*)"    ;
    public String NAMED_REF                               =       "[^ \\t\\n]+"         ;

    public String CODE_KEYWORD                            =       "CODE"                ;
    public String CODE                                    =       "[^ \\t\\n]*"         ;
    public String FORCE_REDUCTION_OP                      =       "#"                   ;
}
