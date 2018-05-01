package edsel.compiler.lex.parser;

import edsel.compiler.lex.automaton.DFA;
import edsel.compiler.lex.text_io.SeekableCharBuffer;
import edsel.compiler.lex.text_io.VarLengthString;
import edsel.compiler.lex.automaton.AutomatonNode;

import static edsel.compiler.lex.parser.TokenizerLangParser.DFA_ExprLexStates.*;

public class TokenizerLangParser<T> {

    public DFA<T> dfa;
    public SeekableCharBuffer input;

    public TokenizerLangParser(DFA<T> dfa) {
        this.dfa = dfa;
    }

    public Token<T> next_tok() {
        VarLengthString token = new VarLengthString();

        AutomatonNode<T> cur_state = dfa.start;

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

    public DFA<T> nfa_to_dfa(SeekableCharBuffer expr) {

    }
}
