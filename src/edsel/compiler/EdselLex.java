package edsel.compiler;

import edsel.lib.lex.parser.EdselexParser;

public class EdselLex extends EdselexParser<EdselLexTokenExpr> {

    public void start_sub_expr(ParseState<StateID, EdselLexTokenExpr> parse_state) {}
    public void finish_sub_expr(ParseState<StateID, EdselLexTokenExpr> parse_state) {}

    public void start_escape(ParseState<StateID, EdselLexTokenExpr> parse_state) {}
    public void finish_escape(ParseState<StateID, EdselLexTokenExpr> parse_state) {}

    public void start_group(ParseState<StateID, EdselLexTokenExpr> parse_state) {}

    public void start_inline_group(ParseState<StateID, EdselLexTokenExpr> parse_state) {}
    public void finish_inline_group(ParseState<StateID, EdselLexTokenExpr> parse_state) {}

    public void start_ref_group(ParseState<StateID, EdselLexTokenExpr> parse_state) {}
    public void continue_ref_group(ParseState<StateID, EdselLexTokenExpr> parse_state) {}
    public void finish_ref_group(ParseState<StateID, EdselLexTokenExpr> parse_state) {}

    public void start_class(ParseState<StateID, EdselLexTokenExpr> parse_state) {}
    public void continue_class(ParseState<StateID, EdselLexTokenExpr> parse_state) {}
    public void finish_class(ParseState<StateID, EdselLexTokenExpr> parse_state) {}

    public void start_complement_class(ParseState<StateID, EdselLexTokenExpr> parse_state) {}
    public void continue_complement_class(ParseState<StateID, EdselLexTokenExpr> parse_state) {}
    public void finish_complement_class(ParseState<StateID, EdselLexTokenExpr> parse_state) {}

    public void start_repetition(ParseState<StateID, EdselLexTokenExpr> parse_state) {}
    public void continue_repetition(ParseState<StateID, EdselLexTokenExpr> parse_state) {}
    public void finish_repetition(ParseState<StateID, EdselLexTokenExpr> parse_state) {}
}
