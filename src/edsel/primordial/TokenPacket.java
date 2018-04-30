package edsel.primordial;

public class TokenPacket {

    public enum TokenType {

        // ==== EDSEL_PROGRAM ===================================================================
        //
        // EDSEL_PROGRAM -> BLOCK [BLOCK_SEPARATOR BLOCK]*

        EDSEL_PROGRAM                       ,

        // ==== BLOCK ===================================================================
        //
        // BLOCK -> VERSION_BLOCK | TOKEN_BLOCK | PRODUCTION_BLOCK | GRAMMAR_BLOCK

        BLOCK                               ,

        VERSION_BLOCK                       ,
        TOKEN_BLOCK                         ,
        GRAMMAR_BLOCK                       ,
        CODE_BLOCK                          ,

        BLOCK_SEPARATOR                     ,   //  [ \t\n]+

        // ==== VERSION_BLOCK ===================================================================
        //
        // VERSION_BLOCK -> (VERSION_REQUIRED VERSION_SPECIFIER) | (THIS_VERSION VERSION_SPECIFIER)

        VERSION_REQUIRED                    ,   //      VERSION_REQUIRED
        THIS_VERSION                        ,   //      THIS_VERSION
        VERSION_SPECIFIER                   ,   //      [^ \t\n]+

        // ==== GRAMMAR_BLOCK ===================================================================
        //
        // GRAMMAR_BLOCK -> GRAMMAR_KEYWORD GRAMMAR_OPEN (EXPANSION_BLOCK)+ [PRODUCTION_OP TRANSLATION_BLOCK] GRAMMAR_CLOSE

        GRAMMAR_KEYWORD                     ,   //      GRAMMAR
        GRAMMAR_OPEN                        ,   //      {
        EXPANSION_BLOCK                     ,
        PRODUCTION_OP                       ,   //      ->
        TRANSLATION_BLOCK                   ,
        GRAMMAR_CLOSE                       ,   //      }

        // ==== EXPANSION_BLOCK ==========================================================================
        //
        // EXPANSION -> (EXPANSION_LITERAL | EXPANSION_IDENTIFIER | DISJUNCTION | STAR_OP | PLUS_OP | GROUP | OPTION | EXPANSION)+

        EXPANSION_LITERAL                   ,   //      [^ \t\n]+       separated string minus the set of identifiers
                                                //                      and the symbols reserved from expansion literals,
                                                //                      with quotations and escapes taken into account

        EXPANSION_IDENTIFIER                ,   //      [_A-Za-z0-9]+

        DISJUNCTION_OP                      ,   //      (EXPANSION | EXPANSION)
        STAR_OP                             ,   //      EXPANSION*
        PLUS_OP                             ,   //      EXPANSION+
        GROUP                               ,   //      (EXPANSION)
        OPTION                              ,   //      [OPTION]

        EXPANSION_ESCAPE                    ,   //      \
        EXPANSION_SEPARATOR                 ,   //      [ \t\n]+
        EXPANSION_SEPARATOR_QUOTE           ,   //      "

        // ==== TRANSLATION ===================================================================
        //
        // TRANSLATION -> (TRANSLATION_LITERAL | EXPANSION_REF)*

        TRANSLATION_LITERAL                 ,    //     [^ \t\n]+       separated string minus the set of expansion references
                                                 //                     (local expansion identifiers and indexed expansions),
                                                 //                     with quotations and escapes taken into account
        EXPANSION_REF                       ,

        TRANSLATION_ESCAPE                  ,    //     \
        TRANSLATION_SEPARATOR               ,    //     [ \t\n]+
        TRANSLATION_SEPARATOR_QUOTE         ,    //     "

        // ==== Code Block ===================================================================

        FORCE_REDUCTION_OP
    }

    public TokenType tok_type;
    public String token;
    public String new_buf;
}
