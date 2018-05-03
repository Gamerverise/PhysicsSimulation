package edsel.lib.parser;

import edsel.lib.lex.automaton.FiniteAutomaton;
import edsel.lib.io.SeekableCharBuffer;
import edsel.lib.io.VarLengthString;

import static edsel.lib.parser.TokenizerLangParser.DFA_ExprLexStates.*;

public class Tokenizer<T> {

    public FiniteAutomaton<T> dfa;
    public SeekableCharBuffer input;

    public Tokenizer(FiniteAutomaton<T> dfa) {
        this.dfa = dfa;
    }

    public Token<T> next_tok() {
        VarLengthString token = new VarLengthString();

        DFA_State<T> cur_state = dfa.start;

        while (true) {
            int chr = input.next();

            if (chr == -1) {
                if (cur_state.is_finish)
                    return new Token<>(cur_state.tok_type, token.toString());
                else
                    return null;
            }

            cur_state = dfa.transition_matrix[cur_state.id][chr];

            if (cur_state == null)
                return null;

            token.chars.add(chr);

            if (cur_state.is_finish) {
                input.backup();
                return new Token<>(cur_state.tok_type, token.convert());
            }
        }
    }

    public FiniteAutomaton<T> nfa_to_dfa(SeekableCharBuffer expr) {

    }
}
