package edsel.compiler.lex.parser;

import edsel.compiler.lex.text_io.SeekableCharBuffer;

public class NFA_DFA_Converter {

    public enum State {
        START,
        ESCAPE,
        LITERAL,
        GROUP_OPEN,
        GROUP_INLINE,
        GROUP_REF,
        EXPECTING_GROUP_INLINE_CLOSE,
        EXPECTING_GROUP_REF_CLOSE,
        CLASS_OPEN,
        EXPECTING_CLASS_CLOSE,
        EXPECTING_COUNT_CLOSE
    }

    public <U> NFA<U> dfa_expr_to_nfa(SeekableCharBuffer expr) {
        State cur_state = START;

        while (true) {
            int chr = expr.next();

            switch (cur_state) {
                case :
                switch (chr) {
                        case :
                    break;
                        case :
                    break;
                        case :
                    break;
                        case :
                    break;
                        case :
                    break;
                        case :
                    break;
                        case :
                    break;
                }
                break;
                case :
                switch (chr) {
                        case :
                    break;
                        case :
                    break;
                        case :
                    break;
                        case :
                    break;
                        case :
                    break;
                        case :
                    break;
                        case :
                    break;
                }
                break;
                case :
                switch (chr) {
                        case :
                    break;
                        case :
                    break;
                        case :
                    break;
                        case :
                    break;
                        case :
                    break;
                        case :
                    break;
                        case :
                    break;
                }
                break;
                case :
                switch (chr) {
                        case :
                    break;
                        case :
                    break;
                        case :
                    break;
                        case :
                    break;
                        case :
                    break;
                        case :
                    break;
                        case :
                    break;
                }
                break;
                case :
                switch (chr) {
                        case :
                    break;
                        case :
                    break;
                        case :
                    break;
                        case :
                    break;
                        case :
                    break;
                        case :
                    break;
                        case :
                    break;
                }
                break;
                case :
                switch (chr) {
                        case :
                    break;
                        case :
                    break;
                        case :
                    break;
                        case :
                    break;
                        case :
                    break;
                        case :
                    break;
                        case :
                    break;
                }
                break;
                case :
                switch (chr) {
                        case :
                    break;
                        case :
                    break;
                        case :
                    break;
                        case :
                    break;
                        case :
                    break;
                        case :
                    break;
                        case :
                    break;
                }
                break;
            }


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
}
