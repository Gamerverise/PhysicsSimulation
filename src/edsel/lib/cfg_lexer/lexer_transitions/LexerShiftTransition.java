package edsel.lib.cfg_lexer.lexer_transitions;

import edsel.lib.cfg_lexer.LexerReduction;
import edsel.lib.cfg_lexer.RegexTerminal;
import edsel.lib.cfg_parser.regex_cfg.transitions.RegexShiftTransition;

public class LexerShiftTransition
        extends RegexShiftTransition<LexerReduction>
{
    public LexerShiftTransition(
            RegexTerminal<LexerReduction> terminal,
            LexerStateStackEntry new_state)
    {
        super(terminal, new_state);
    }
}
