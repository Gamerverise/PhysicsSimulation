package edsel.compiler.lex.parser;

import edsel.compiler.lex.automata_state.DFA_State;
import edsel.compiler.lex.automaton.NFA;
import edsel.compiler.lex.text_io.SeekableCharBuffer;

import java.util.Stack;

import static edsel.compiler.lex.parser.NFA_Lang_Parser.StateID.*;

public class NFA_Lang_Parser {

    public enum StateID {
        START_SUB_EXPR_ID,
        ESCAPE_ID,
        GROUP_OPEN_ID,
        GROUP_INLINE_ID,
        GROUP_REF_ID,
        GROUP_REF_CLOSE_ID,
        CLASS_ID,
        COMPLIMENT_CLASS_ID,
        COUNT_ID,
        __SIZE__
    }

    public enum TokenType {NO_TYPE}

    public static DFA_State<StateID> START_SUB_EXPR = new DFA_State<>(START_SUB_EXPR_ID, true);
    public static DFA_State<StateID> ESCAPE = new DFA_State<>(StateID.ESCAPE_ID, false);
    public static DFA_State<StateID> GROUP_OPEN = new DFA_State<>(StateID.GROUP_OPEN_ID, false);
    public static DFA_State<StateID> GROUP_INLINE = new DFA_State<>(StateID.GROUP_INLINE_ID, false);
    public static DFA_State<StateID> GROUP_REF = new DFA_State<>(StateID.GROUP_REF_ID, false);
    public static DFA_State<StateID> GROUP_REF_CLOSE = new DFA_State<>(StateID.GROUP_REF_CLOSE_ID, false);
    public static DFA_State<StateID> CLASS = new DFA_State<>(StateID.CLASS_ID, false);
    public static DFA_State<StateID> COMPLIMENT_CLASS = new DFA_State<>(StateID.COMPLIMENT_CLASS_ID, false);
    public static DFA_State<StateID> COUNT_OPEN = new DFA_State<>(StateID.COUNT_ID, false);
    
//    @SuppressWarnings("unchecked")
//    DFA_State<StateID>[][] transition_matrix = new DFA_State[StateID.__SIZE__.ordinal()][];
//
//    {
//        for (int i = 0; i < transition_matrix.length; i++) {
//            //noinspection unchecked
//            transition_matrix[i] = new DFA_State[256];
//
//            for (int j = 0; j < 256; j++)
//                transition_matrix[i][j] = START_SUB_EXPR;
//        }
//
//        transition_matrix[START_SUB_EXPR_ID.ordinal()]['\\'].id = START_SUB_EXPR_ID;
//        transition_matrix[START_SUB_EXPR_ID.ordinal()]['('].id = GROUP_OPEN_ID;
//        transition_matrix[START_SUB_EXPR_ID.ordinal()]['['].id = CLASS_OPEN_ID;
//        transition_matrix[START_SUB_EXPR_ID.ordinal()]['{'].id = COUNT_ID;
//
//        transition_matrix[GROUP_OPEN_ID.ordinal()][':'].id = GROUP_REF_ID;
//        transition_matrix[GROUP_OPEN_ID.ordinal()]['\\'].id = ESCAPE_ID;
//    }

    public class Invalid_NFA_Description extends Exception {}

    public <U> NFA<U> parse_dfa_description(SeekableCharBuffer expr)
            throws Invalid_NFA_Description
    {
        Stack<DFA_State<StateID>> state_stack = new Stack<>();
        
        state_stack.push(START_SUB_EXPR);

        while (true) {
            int chr = expr.next();

            switch (state_stack.peek().id) {

                case START_SUB_EXPR_ID:
                    sub_expr_start_transition(state_stack, chr);
                    break;

                case ESCAPE_ID:
                    escape_transition(state_stack, chr);
                    break;

                case GROUP_OPEN_ID:
                    group_open_transition(state_stack, chr);
                    break;

                case GROUP_INLINE_ID:
                    group_inline_transition(state_stack, chr);
                    break;

                case GROUP_REF_ID:
                    group_ref_transition(state_stack, chr);
                    break;

                case GROUP_REF_CLOSE_ID:
                    group_ref_close_transition(state_stack, chr);
                    break;

                case CLASS_ID:
                    class_transition(state_stack, chr);
                    break;

                case COMPLIMENT_CLASS_ID:
                    compliment_class_transition(state_stack, chr);
                    break;

                case COUNT_ID:
                    count_transition(state_stack, chr);
                    break;
            }

            DFA_State<StateID> state = state_stack.peek();

            if (chr == -1) {
                if (state_stack.size() == 1)
                    return ;
                else
                    return null;
            }
        }
    }

    public void sub_expr_start_transition(Stack<DFA_State<StateID>> state_stack, int chr)
            throws Invalid_NFA_Description
    {
        switch (chr) {
            case '\\':
                state_stack.push(ESCAPE);
                break;

            case '(':
                state_stack.push(GROUP_OPEN);
                break;

            case ')':
                throw new Invalid_NFA_Description();

            case '[':
                state_stack.push(CLASS);
                break;

            case ']':
                throw new Invalid_NFA_Description();

            case '<':
                state_stack.push(CLASS);
                break;

            case '>':
                throw new Invalid_NFA_Description();

            case '{':
                state_stack.push(COUNT_OPEN);
                break;

            case '}':
                throw new Invalid_NFA_Description();

            default:
                break;
        }
    }

    public void escape_transition(Stack<DFA_State<StateID>> state_stack, int chr)
            throws Invalid_NFA_Description
    {
        state_stack.pop();
    }

    public void group_open_transition(Stack<DFA_State<StateID>> state_stack, int chr)
            throws Invalid_NFA_Description
    {
        state_stack.pop();

        switch (chr) {
            case ':':
                state_stack.push(GROUP_REF);
                break;

            case ')':
                state_stack.pop();
                break;

            default:
                state_stack.push(GROUP_INLINE);
                sub_expr_start_transition(state_stack, chr);
                break;
        }
    }

    public void group_inline_transition(Stack<DFA_State<StateID>> state_stack, int chr)
            throws Invalid_NFA_Description
    {
        switch (chr) {
            case ')':
                state_stack.pop();
                break;

            default:
                sub_expr_start_transition(state_stack, chr);
                break;
        }
    }

    public void group_ref_transition(Stack<DFA_State<StateID>> state_stack, int chr)
            throws Invalid_NFA_Description
    {
        switch (chr) {
            case ':':
                state_stack.push(GROUP_REF_CLOSE);
                break;

            default:
                sub_expr_start_transition(state_stack, chr);
                break;
        }
    }

    public void group_ref_close_transition(Stack<DFA_State<StateID>> state_stack, int chr)
            throws Invalid_NFA_Description
    {
        switch (chr) {
            case ')':
                state_stack.pop();
                break;

            default:
                sub_expr_start_transition(state_stack, chr);
                break;
        }
    }

    public void class_transition(Stack<DFA_State<StateID>> state_stack, int chr)
            throws Invalid_NFA_Description
    {
        switch (chr) {
            case ']':
                state_stack.pop();
                break;

            default:
                sub_expr_start_transition(state_stack, chr);
                break;
        }
    }

    public void compliment_class_transition(Stack<DFA_State<StateID>> state_stack, int chr)
            throws Invalid_NFA_Description
    {
        switch (chr) {
            case '>':
                state_stack.pop();
                break;

            default:
                sub_expr_start_transition(state_stack, chr);
                break;
        }
    }

    public void count_transition(Stack<DFA_State<StateID>> state_stack, int chr)
            throws Invalid_NFA_Description
    {
        switch (chr) {
            case '}':
                state_stack.pop();
                break;

            default:
                sub_expr_start_transition(state_stack, chr);
                break;
        }
    }
}
