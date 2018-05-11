package edsel.lib.cfg_lexer.lexer_transitions;


import edsel.lib.cfg_lexer.LexerReduction;
import edsel.lib.cfg_lexer.RegexTerminal;
import edsel.lib.cfg_parser.regex_cfg.transitions.RegexReduceTransition;

public class LexerReduceTransition
        extends RegexReduceTransition<LexerReduction>
{
    public LexerReduceTransition(
            RegexTerminal<LexerReduction> terminal,
            LexerStateStackEntry<LexerReduction> new_state,
            RegexProduction<LexerReduction> production,
            LexerReduction reduction)
    {
        super(terminal, new_state, production);
    }
}
