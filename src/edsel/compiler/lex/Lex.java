package edsel.compiler.lex;

import static edsel.compiler.lex.Lex.DFA_ExprLexStates.*;

public class Lex<T> {

    public DFA<T> dfa;
    public LexBuffer input;

    public Lex(DFA<T> dfa) {
        this.dfa = dfa;
    }

    public AutomatonToken<T> next_tok() {
        LexString token = new LexString();

        AutomatonState<T> cur_state = dfa.start;

        while (true) {
            int chr = input.next();

            if (chr == -1) {
                if (cur_state.is_finish)
                    return new AutomatonToken<>(cur_state.tok_type, token.toString());
                else
                    return null;
            }

            cur_state = dfa.transition_matrix[cur_state.id][chr];

            if (cur_state == null)
                return null;

            token.chars.add(chr);

            if (cur_state.is_finish) {
                input.backup();
                return new AutomatonToken<>(cur_state.tok_type, token.convert());
            }
        }
    }

    public DFA<T> nfa_to_dfa(LexBuffer expr) {

    }
}
