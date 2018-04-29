===== Assembly Language ===========================================================================

required_version identifier EOS                     0
this_version identifier EOS                         0

create_token identifier <reg_expr> EOS
replace_token identifier <reg_expr> EOS

create_production identifier expansion [-> translation] EOS
replace_production identifier expansion [-> translation] EOS

force_reduction

identifier                              [_A-Za-z0-9]+

expansion -> (identifier | expansion_literal)*

expansion_literal                       separated string minus the set of identifiers
                                        and the symbols reserved from literals,
                                        with quotations and escapes taken into account

translation -> translation_token+

translation_token -> named_expansions_ref | indexed_expansion_ref | translation_literal

named_expansion_ref                     local expansion identifier

indexed_expansion_ref                   :<pos_int>

translation_literal                     separated string minus the set of expansion references
                                        (local expansion identifiers and indexed expansions),
                                        with quotations and escapes taken into account

===== Symbols Reserved from Literals ==================================================================

separator                       [ \t\n]+
escape                          \
separator_quotes                " "
identifier_delims               < >
option_delims                   [ ]

disjunction_op                  |
star_op                         *
plus_op                         +
group_op                        ( )

production_op                   ->

===== Symbols Reserved from Translation =================================================================

escape                          \
expansion_ref_quotes            " "
separator_quotes                " "
expansion_index_op              :

EOS                             ; <EOF>

============================================================================================================

force_reduction_op              #
