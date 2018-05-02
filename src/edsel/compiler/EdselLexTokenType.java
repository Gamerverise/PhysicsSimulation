package edsel.compiler;

public enum EdselLexTokenType {

    BLOCK_SEPARATOR                     ,   //      [ \t\n]+
    VERSION_REQUIRED                    ,   //      VERSION_REQUIRED
    THIS_VERSION                        ,   //      THIS_VERSION
    VERSION_SPECIFIER                   ,   //      [^ \t\n]+
    LEX_KEYWORD                         ,   //      LEX
    TOKEN_IDENTIFIER                    ,   //      [_A-Za-z0-9]+
    MATCH_EXPRESSION                    ,   //      regular expressions extended to allow sub-expressions
    GRAMMAR_KEYWORD                     ,   //      GRAMMAR
    GRAMMAR_LHS                         ,   //      [^ \t\n]+
    PRODUCTION_OP                       ,   //      ->
    TRANSLATION_OP                      ,   //      :
    GRAMMAR_ESCAPE                      ,   //      \
    GRAMMAR_SEPARATOR                   ,   //      [ \t\n]+
    GRAMMAR_QUOTE                       ,   //      "
    EXPANSION_LITERAL                   ,   //      [^ \t\n]+       separated string minus the set of identifiers
    EXPANSION_IDENTIFIER                ,   //      <[_A-Za-z0-9]+>
    EXPANSION_ESCAPE                    ,   //      \
    EXPANSION_SEPARATOR                 ,   //      [ \t\n]+
    EXPANSION_QUOTE                     ,   //      "
    TRANSLATION_LITERAL                 ,   //      [^ \t\n]+       separated string minus the set of expansion references
    TRANSLATION_ESCAPE                  ,   //      \
    TRANSLATION_SEPARATOR               ,   //      [ \t\n]+
    TRANSLATION_QUOTE                   ,   //      "
    INDEX_REF                           ,   //      :0|([1-9][0-9]*)
    NAMED_REF                           ,   //      [^ \t\n]+
    CODE_KEYWORD                        ,   //      CODE
    CODE                                ,   //      [^ \t\n]*
    FORCE_REDUCTION_OP                  ,   //      #
    CODE_ESCAPE                         ,   //      \
    CODE_QUOTE                              //      "
}
