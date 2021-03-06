package edsel.versions._1_primordial.parser.edselex_grammar;

public enum EdselexProducitonType {

    EDSELEX,

    SUB_EXPR,

    OR,
    STAR,
    PLUS,
    QUESTION,

    GROUP,
    INLINE_GROUP,
    REF_GROUP,

    CLASS,
    COMPLEMENT_CLASS,
    REPETITION
}

// Note: The empty expression only accepts the empty string

// Note:    ...                         is the list operator. It accepts a comma-separated list of expressions
//
//          SUB_EXPR COLON IDENTIFIER   is the function operator. It applies the function to the sub-expression
//                                      before the first colon
//
//          SUB_EXPR? COLON IDENTIFIER  is the regular expression operator, which creates a token inline

// EDSELEX
//     (SUB_EXPR | ESCAPED_SUB_EXPR)*
//
// SUB_EXPR
//     [LITERAL OR STAR PLUS QUESTION GROUP CLASS COMPLEMENT_CLASS REPETITION ESCAPED_SUB_EXPR]+
//
// LITERAL
//     NON_RESERVED_CHAR | ESCAPED_RESERVED_CHAR
//
// ESCAPED_RESERVED_CHAR
//     BACKSLASH RESERVED_CHAR
//
// OR
//     SUB_EXPR OR SUB_EXPR
//
// STAR
//     SUB_EXPR STAR
//
// PLUS
//     SUB_EXPR PLUS
//
// QUESTION
//     SUB_EXPR QUESTION
//
// GROUP
//     OPEN_PAREN (SUB_EXPR | REF) CLOSE_PAREN
//
// REF
//     COLON IDENTIFIER COLON
//
// CLASS
//     OPEN_BRACKET CARET? (NOT_CLOSE_BRACKET | ESCAPED_CLOSE_BRACKET)+:class_spec CLOSE_CLASS
//
// QUOTE_CLASS
//     LESS (LITERAL)+ GREATER
//
// REPETITION
//     SUB_EXPR OPEN_BRACE [0-9,]:repetition_list CLOSE_BRACE



// ESCAPED_SUB_EXPR
//     AT TWIDDLED_SUB_EXPR AT
//
// TWIDDLE_SUB_EXPR
//     [TWIDDLE_LITERAL TWIDDLE_OR TWIDDLE_STAR TWIDDLE_PLUS TWIDDLE_QUESTION TWIDDLE_GROUP
//      TWIDDLE_CLASS TWIDDLE_COMPLEMENT_CLASS TWIDDLE_REPETITION UNESCAPED_SUB_EXPR]+
//
// UNESCAPED_SUB_EXPR
//     TWIDDLE_AT SUB_EXPR TWIDDLE_AT
//
// TWIDDLE_LITERAL
//     NOT_TWIDDLE | (TWIDDLE TWIDDLE)
//
// TWIDDLE_OR
//     TWIDDLE_SUB_EXPR TWIDDLE_OR TWIDDLE_SUB_EXPR
//
// TWIDDLE_STAR
//     TWIDDLE_SUB_EXPR TWIDDLE_STAR
//
// TWIDDLE_PLUS
//     TWIDDLE_SUB_EXPR TWIDDLE_PLUS
//
// TWIDDLE_QUESTION
//     TWIDDLE_SUB_EXPR TWIDDLE_QUESTION
//
// TWIDDLE_GROUP
//     TWIDDLE_OPEN_PAREN (TWIDDLE_SUB_EXPR | TWIDDLE_REF) TWIDDLE_CLOSE_PAREN
//
// TWIDDLE_REF
//     TWIDDLE_COLON IDENTIFIER TWIDDLE_COLON
//
// TWIDDLE_CLASS
//     TWIDDLE_OPEN_BRACKET TWIDDLE_CARET? (NOT_TWIDDLE_CLOSE_BRACKET)+:class_spec TWIDDLE_CLOSE_CLASS
//
// TWIDDLE_QUOTE_CLASS
//     TWIDDLE_LESS (TWIDDLE_LITERAL)+ TWIDDLE_GREATER
//
// TWIDDLE_REPETITION
//     TWIDDLE_SUB_EXPR TWIDDLE_OPEN_BRACE [0-9,]:repetition_list TWIDDLE_CLOSE_BRACE
