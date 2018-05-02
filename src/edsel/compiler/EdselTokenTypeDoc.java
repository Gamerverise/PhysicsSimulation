package edsel.compiler;

public enum EdselTokenTypeDoc {

    GRAMMAR_ESCAPE                      ,   //      \
    GRAMMAR_SEPARATOR                   ,   //      [ \t\n]+
    GRAMMAR_QUOTE                       ,   //      "

    // ==== EDSEL_PROGRAM ===================================================================
    //
    // EDSEL_PROGRAM -> BLOCK*

    EDSEL_PROGRAM                       ,

    BLOCK                               ,

    // ==== BLOCK ===================================================================
    //
    // BLOCK -> VERSION_BLOCK | LEX_BLOCK | GRAMMAR_BLOCK | CODE_BLOCK

    VERSION_BLOCK                       ,
    LEX_BLOCK                           ,
    GRAMMAR_BLOCK                       ,
    CODE_BLOCK                          ,

    // ==== VERSION_BLOCK ===================================================================
    //
    // VERSION_BLOCK -> (VERSION_REQUIRED VERSION_SPECIFIER) | (THIS_VERSION VERSION_SPECIFIER)

    VERSION_REQUIRED                    ,   //      VERSION_REQUIRED
    THIS_VERSION                        ,   //      THIS_VERSION
    VERSION_SPECIFIER                   ,   //      [^ \t\n]+

    // ==== LEX_BLOCK =======================================================================
    //
    // LEX_BLOCK -> LEX_KEYWORD LEX_DEF*

    LEX_KEYWORD                         ,   //      LEX
    LEX_DEF                             ,

    // ==== LEX_DEF ==========================================================================
    //
    // LEX_DEF -> TOKEN_IDENTIFIER MATCH_EXPRESSION

    TOKEN_IDENTIFIER                    ,   // [_A-Za-z0-9]+
    MATCH_EXPRESSION                    ,   // regular expressions extended to allow sub-expressions
                                            // defined within an Edsel program, the sub-expressions may
                                            // be used within (: :)

    // ==== GRAMMAR_BLOCK ===================================================================
    //
    // GRAMMAR_BLOCK -> GRAMMAR_KEYWORD GRAMMAR_LHS
    //                  PRODUCTION_OP (EXPANSION_BLOCK)+
    //                  [TRANSLATION_OP TRANSLATION_BLOCK]

    GRAMMAR_KEYWORD                     ,   //      GRAMMAR
    GRAMMAR_LHS                         ,   //      [^ \t\n]+
    PRODUCTION_OP                       ,   //      ->
    EXPANSION                           ,
    TRANSLATION_OP                      ,   //      :
    TRANSLATION                         ,

    // ==== EXPANSION_BLOCK ==========================================================================
    //
    // EXPANSION -> (EXPANSION_LITERAL | EXPANSION_IDENTIFIER | DISJUNCTION | STAR_OP | PLUS_OP | GROUP | OPTION | EXPANSION)+

    EXPANSION_LITERAL                   ,   //      [^ \t\n]+       separated string minus the set of identifiers
                                            //                      and the symbols reserved from expansion literals,
                                            //                      with quotations and escapes taken into account

    EXPANSION_IDENTIFIER                ,   //      <[_A-Za-z0-9]+>

    DISJUNCTION_OP                      ,   //      (EXPANSION | EXPANSION)
    STAR_OP                             ,   //      EXPANSION*
    PLUS_OP                             ,   //      EXPANSION+
    GROUP                               ,   //      (EXPANSION)
    OPTION                              ,   //      [OPTION]

    // ==== TRANSLATION_BLOCK ===================================================================
    //
    // TRANSLATION -> (TRANSLATION_LITERAL | EXPANSION_REF)*

    TRANSLATION_LITERAL                 ,   //     [^ \t\n]+       separated string minus the set of expansion references
                                            //                     (local expansion identifiers and indexed expansions),
                                            //                     with quotations and escapes taken into account
    EXPANSION_REF                       ,

    // ==== EXPANSION_REF ==================================================================
    //
    // EXPANSION_REF -> INDEX_REF | NAMED_REF

    INDEX_REF                           ,   //     :0|([1-9][0-9]*)
    NAMED_REF                           ,   //     [^ \t\n]+

    // ==== CODE_BLOCK =====================================================================
    //
    // CODE_BLOCK -> CODE_KEYWORD (CODE | FORCE_REDUCTION_OP)*

    CODE_KEYWORD                        ,   //     CODE
    CODE                                ,   //     [^ \t\n]*
    FORCE_PRODUCTION                    ,   //     ->
    FORCE_REDUCTION                         //     #
}
