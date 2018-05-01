package edsel.compiler.lex.parser;

import edsel.compiler.lex.automaton.FiniteAutomaton;
import edsel.compiler.lex.automaton.FiniteAutomatonState;
import edsel.compiler.lex.text_io.SeekableCharBuffer;

import java.util.Stack;

import static edsel.compiler.lex.parser.NFA_Lang_Parser.StateID.*;

public class NFA_Lang_Parser {

    public static class Invalid_NFA_Description extends Exception {}

    public interface TransitionFunc {
        void call(ParseState<StateID> parse_state)
                throws Invalid_NFA_Description;
    }

    // =========================================================================================

    public enum StateID {
        SUB_EXPR_ID,
        ESCAPE_ID,
        
        GROUP_OPEN_ID,
        INLINE_GROUP_ID,
        REF_GROUP_ID,
        REF_GROUP_CLOSE_ID,
        
        CLASS_ID,
        COMPLEMENT_CLASS_ID,
        REPETITION_ID,
        __SIZE__;

        public static int O(StateID id) {
            return id.ordinal();
        }
    }

    // =========================================================================================

    public static class ParseState<ID_TYPE> {

        SeekableCharBuffer input;
        Stack<FiniteAutomatonState<ID_TYPE>> state_stack = new Stack<>();
        FiniteAutomaton<ID_TYPE> nfa = new FiniteAutomaton<>();

        ParseState(SeekableCharBuffer input) {
            this.input = input;
        }
    }

    // =========================================================================================

    public static FiniteAutomatonState<StateID> SUB_EXPR_STATE = new FiniteAutomatonState<>(SUB_EXPR_ID, true);
    public static FiniteAutomatonState<StateID> ESCAPE_STATE = new FiniteAutomatonState<>(StateID.ESCAPE_ID, false);
    
    public static FiniteAutomatonState<StateID> GROUP_OPEN_STATE = new FiniteAutomatonState<>(StateID.GROUP_OPEN_ID, false);
    public static FiniteAutomatonState<StateID> INLINE_GROUP_STATE = new FiniteAutomatonState<>(StateID.INLINE_GROUP_ID, false);
    public static FiniteAutomatonState<StateID> REF_GROUP_STATE = new FiniteAutomatonState<>(StateID.REF_GROUP_ID, false);
    public static FiniteAutomatonState<StateID> REF_GROUP_CLOSE_STATE = new FiniteAutomatonState<>(StateID.REF_GROUP_CLOSE_ID, false);
    
    public static FiniteAutomatonState<StateID> CLASS_STATE = new FiniteAutomatonState<>(StateID.CLASS_ID, false);
    public static FiniteAutomatonState<StateID> COMPLEMENT_CLASS_STATE = new FiniteAutomatonState<>(StateID.COMPLEMENT_CLASS_ID, false);
    public static FiniteAutomatonState<StateID> REPETITION_STATE = new FiniteAutomatonState<>(StateID.REPETITION_ID, false);

    // =========================================================================================
    
    public static int star = 257;

    @SuppressWarnings("unchecked")
    public static TransitionFunc[][] transition_matrix = new TransitionFunc[StateID.__SIZE__.ordinal()][];

    public void set_transition(FiniteAutomatonState<StateID> state, int chr, TransitionFunc tf) {
        transition_matrix[state.id.ordinal()][chr] = tf;
    }

    {
        for (int i = 0; i < transition_matrix.length; i++) {
            //noinspection unchecked
            transition_matrix[i] = new TransitionFunc[star];
        }

        set_transition(SUB_EXPR_STATE, '.', NFA_Lang_Parser::finish_sub_expr);
        set_transition(SUB_EXPR_STATE, '\\', NFA_Lang_Parser::start_escape);
        set_transition(SUB_EXPR_STATE, '?', NFA_Lang_Parser::finish_sub_expr);
        set_transition(SUB_EXPR_STATE, '+', NFA_Lang_Parser::finish_sub_expr);
        set_transition(SUB_EXPR_STATE, '*', NFA_Lang_Parser::finish_sub_expr);
        set_transition(SUB_EXPR_STATE, '(', NFA_Lang_Parser::start_group);
        set_transition(SUB_EXPR_STATE, ':', NFA_Lang_Parser::error);
        set_transition(SUB_EXPR_STATE, ')', NFA_Lang_Parser::error);
        set_transition(SUB_EXPR_STATE, '[', NFA_Lang_Parser::start_class);
        set_transition(SUB_EXPR_STATE, ']', NFA_Lang_Parser::error);
        set_transition(SUB_EXPR_STATE, '<', NFA_Lang_Parser::start_complement_class);
        set_transition(SUB_EXPR_STATE, '>', NFA_Lang_Parser::error);
        set_transition(SUB_EXPR_STATE, '{', NFA_Lang_Parser::start_repetition);
        set_transition(SUB_EXPR_STATE, '}', NFA_Lang_Parser::error);
        set_transition(SUB_EXPR_STATE, star, NFA_Lang_Parser::finish_sub_expr);

        set_transition(ESCAPE_STATE, star, NFA_Lang_Parser::finish_escape);

        set_transition(GROUP_OPEN_STATE, '.', NFA_Lang_Parser::start_inline_group);
        set_transition(GROUP_OPEN_STATE, '\\', NFA_Lang_Parser::start_escape);
        set_transition(GROUP_OPEN_STATE, '?', NFA_Lang_Parser::error);
        set_transition(GROUP_OPEN_STATE, '+', NFA_Lang_Parser::error);
        set_transition(GROUP_OPEN_STATE, '*', NFA_Lang_Parser::error);
        set_transition(GROUP_OPEN_STATE, '(', NFA_Lang_Parser::start_inline_group);
        set_transition(GROUP_OPEN_STATE, ':', NFA_Lang_Parser::start_ref_group);
        set_transition(GROUP_OPEN_STATE, ')', NFA_Lang_Parser::finish_inline_group);
        set_transition(GROUP_OPEN_STATE, '[', NFA_Lang_Parser::start_class);
        set_transition(GROUP_OPEN_STATE, ']', NFA_Lang_Parser::error);
        set_transition(GROUP_OPEN_STATE, '<', NFA_Lang_Parser::start_complement_class);
        set_transition(GROUP_OPEN_STATE, '>', NFA_Lang_Parser::error);
        set_transition(GROUP_OPEN_STATE, '{', NFA_Lang_Parser::start_repetition);
        set_transition(GROUP_OPEN_STATE, '}', NFA_Lang_Parser::error);
        set_transition(GROUP_OPEN_STATE, star, NFA_Lang_Parser::start_inline_group);

        set_transition(INLINE_GROUP_STATE, '.', NFA_Lang_Parser::start_sub_expr);
        set_transition(INLINE_GROUP_STATE, '\\', NFA_Lang_Parser::start_escape);
        set_transition(INLINE_GROUP_STATE, '?', NFA_Lang_Parser::finish_sub_expr);
        set_transition(INLINE_GROUP_STATE, '+', NFA_Lang_Parser::finish_sub_expr);
        set_transition(INLINE_GROUP_STATE, '*', NFA_Lang_Parser::finish_sub_expr);
        set_transition(INLINE_GROUP_STATE, '(', NFA_Lang_Parser::start_group);
        set_transition(INLINE_GROUP_STATE, ':', NFA_Lang_Parser::error);
        set_transition(INLINE_GROUP_STATE, ')', NFA_Lang_Parser::finish_inline_group);
        set_transition(INLINE_GROUP_STATE, '[', NFA_Lang_Parser::start_class);
        set_transition(INLINE_GROUP_STATE, ']', NFA_Lang_Parser::error);
        set_transition(INLINE_GROUP_STATE, '<', NFA_Lang_Parser::start_complement_class);
        set_transition(INLINE_GROUP_STATE, '>', NFA_Lang_Parser::error);
        set_transition(INLINE_GROUP_STATE, '{', NFA_Lang_Parser::error);
        set_transition(INLINE_GROUP_STATE, '}', NFA_Lang_Parser::error);
        set_transition(INLINE_GROUP_STATE, star, NFA_Lang_Parser::start_sub_expr);

        set_transition(REF_GROUP_STATE, '.', NFA_Lang_Parser::error);
        set_transition(REF_GROUP_STATE, '\\', NFA_Lang_Parser::error);
        set_transition(REF_GROUP_STATE, '?', NFA_Lang_Parser::error);
        set_transition(REF_GROUP_STATE, '+', NFA_Lang_Parser::error);
        set_transition(REF_GROUP_STATE, '*', NFA_Lang_Parser::error);
        set_transition(REF_GROUP_STATE, '(', NFA_Lang_Parser::error);
        set_transition(REF_GROUP_STATE, ':', NFA_Lang_Parser::finish_ref_group);
        set_transition(REF_GROUP_STATE, ')', NFA_Lang_Parser::error);
        set_transition(REF_GROUP_STATE, '[', NFA_Lang_Parser::error);
        set_transition(REF_GROUP_STATE, ']', NFA_Lang_Parser::error);
        set_transition(REF_GROUP_STATE, '<', NFA_Lang_Parser::error);
        set_transition(REF_GROUP_STATE, '>', NFA_Lang_Parser::error);
        set_transition(REF_GROUP_STATE, '{', NFA_Lang_Parser::error);
        set_transition(REF_GROUP_STATE, '}', NFA_Lang_Parser::error);
        set_transition(REF_GROUP_STATE, star, NFA_Lang_Parser::continue_ref_group);

        set_transition(REF_GROUP_CLOSE_STATE, '.', NFA_Lang_Parser::error);
        set_transition(REF_GROUP_CLOSE_STATE, '\\', NFA_Lang_Parser::error);
        set_transition(REF_GROUP_CLOSE_STATE, '?', NFA_Lang_Parser::error);
        set_transition(REF_GROUP_CLOSE_STATE, '+', NFA_Lang_Parser::error);
        set_transition(REF_GROUP_CLOSE_STATE, '*', NFA_Lang_Parser::error);
        set_transition(REF_GROUP_CLOSE_STATE, '(', NFA_Lang_Parser::error);
        set_transition(REF_GROUP_CLOSE_STATE, ':', NFA_Lang_Parser::error);
        set_transition(REF_GROUP_CLOSE_STATE, ')', NFA_Lang_Parser::finish_ref_group);
        set_transition(REF_GROUP_CLOSE_STATE, '[', NFA_Lang_Parser::error);
        set_transition(REF_GROUP_CLOSE_STATE, ']', NFA_Lang_Parser::error);
        set_transition(REF_GROUP_CLOSE_STATE, '<', NFA_Lang_Parser::error);
        set_transition(REF_GROUP_CLOSE_STATE, '>', NFA_Lang_Parser::error);
        set_transition(REF_GROUP_CLOSE_STATE, '{', NFA_Lang_Parser::error);
        set_transition(REF_GROUP_CLOSE_STATE, '}', NFA_Lang_Parser::error);
        set_transition(REF_GROUP_CLOSE_STATE, star, NFA_Lang_Parser::error);

        set_transition(CLASS_STATE, '.', NFA_Lang_Parser::error);
        set_transition(CLASS_STATE, '\\', NFA_Lang_Parser::start_escape);
        set_transition(CLASS_STATE, '?', NFA_Lang_Parser::error);
        set_transition(CLASS_STATE, '+', NFA_Lang_Parser::error);
        set_transition(CLASS_STATE, '*', NFA_Lang_Parser::error);
        set_transition(CLASS_STATE, '(', NFA_Lang_Parser::error);
        set_transition(CLASS_STATE, ':', NFA_Lang_Parser::error);
        set_transition(CLASS_STATE, ')', NFA_Lang_Parser::error);
        set_transition(CLASS_STATE, '[', NFA_Lang_Parser::error);
        set_transition(CLASS_STATE, ']', NFA_Lang_Parser::finish_class);
        set_transition(CLASS_STATE, '<', NFA_Lang_Parser::error);
        set_transition(CLASS_STATE, '>', NFA_Lang_Parser::error);
        set_transition(CLASS_STATE, '{', NFA_Lang_Parser::error);
        set_transition(CLASS_STATE, '}', NFA_Lang_Parser::error);
        set_transition(CLASS_STATE, star, NFA_Lang_Parser::continue_class);

        set_transition(COMPLEMENT_CLASS_STATE, '.', NFA_Lang_Parser::error);
        set_transition(COMPLEMENT_CLASS_STATE, '\\', NFA_Lang_Parser::start_escape);
        set_transition(COMPLEMENT_CLASS_STATE, '?', NFA_Lang_Parser::error);
        set_transition(COMPLEMENT_CLASS_STATE, '+', NFA_Lang_Parser::error);
        set_transition(COMPLEMENT_CLASS_STATE, '*', NFA_Lang_Parser::error);
        set_transition(COMPLEMENT_CLASS_STATE, '(', NFA_Lang_Parser::error);
        set_transition(COMPLEMENT_CLASS_STATE, ':', NFA_Lang_Parser::error);
        set_transition(COMPLEMENT_CLASS_STATE, ')', NFA_Lang_Parser::error);
        set_transition(COMPLEMENT_CLASS_STATE, '[', NFA_Lang_Parser::error);
        set_transition(COMPLEMENT_CLASS_STATE, ']', NFA_Lang_Parser::error);
        set_transition(COMPLEMENT_CLASS_STATE, '<', NFA_Lang_Parser::error);
        set_transition(COMPLEMENT_CLASS_STATE, '>', NFA_Lang_Parser::finish_complement_class);
        set_transition(COMPLEMENT_CLASS_STATE, '{', NFA_Lang_Parser::error);
        set_transition(COMPLEMENT_CLASS_STATE, '}', NFA_Lang_Parser::error);
        set_transition(COMPLEMENT_CLASS_STATE, star, NFA_Lang_Parser::continue_complement_class);

        set_transition(REPETITION_STATE, '.', NFA_Lang_Parser::error);
        set_transition(REPETITION_STATE, '\\', NFA_Lang_Parser::error);
        set_transition(REPETITION_STATE, '?', NFA_Lang_Parser::error);
        set_transition(REPETITION_STATE, '+', NFA_Lang_Parser::error);
        set_transition(REPETITION_STATE, '*', NFA_Lang_Parser::error);
        set_transition(REPETITION_STATE, '(', NFA_Lang_Parser::error);
        set_transition(REPETITION_STATE, ':', NFA_Lang_Parser::error);
        set_transition(REPETITION_STATE, ')', NFA_Lang_Parser::error);
        set_transition(REPETITION_STATE, '[', NFA_Lang_Parser::error);
        set_transition(REPETITION_STATE, ']', NFA_Lang_Parser::error);
        set_transition(REPETITION_STATE, '<', NFA_Lang_Parser::error);
        set_transition(REPETITION_STATE, '>', NFA_Lang_Parser::error);
        set_transition(REPETITION_STATE, '{', NFA_Lang_Parser::error);
        set_transition(REPETITION_STATE, '}', NFA_Lang_Parser::finish_repetition);
        set_transition(REPETITION_STATE, star, NFA_Lang_Parser::error);

        set_transition(REPETITION_STATE, '0', NFA_Lang_Parser::continue_repetition);
        set_transition(REPETITION_STATE, '1', NFA_Lang_Parser::continue_repetition);
        set_transition(REPETITION_STATE, '2', NFA_Lang_Parser::continue_repetition);
        set_transition(REPETITION_STATE, '3', NFA_Lang_Parser::continue_repetition);
        set_transition(REPETITION_STATE, '4', NFA_Lang_Parser::continue_repetition);
        set_transition(REPETITION_STATE, '5', NFA_Lang_Parser::continue_repetition);
        set_transition(REPETITION_STATE, '6', NFA_Lang_Parser::continue_repetition);
        set_transition(REPETITION_STATE, '7', NFA_Lang_Parser::continue_repetition);
        set_transition(REPETITION_STATE, '8', NFA_Lang_Parser::continue_repetition);
        set_transition(REPETITION_STATE, '9', NFA_Lang_Parser::continue_repetition);
    }

    // =========================================================================================

    public static <U> FiniteAutomaton<U> parse_dfa_description(SeekableCharBuffer expr)
            throws Invalid_NFA_Description
    {
        ParseState<StateID> parse_state = new ParseState<>(expr);
        Stack<FiniteAutomatonState<StateID>> state_stack = new Stack<>();

        state_stack.push(SUB_EXPR_STATE);

        while (true) {
            int chr = expr.next();
            
            transition_matrix[state_stack.peek().id.ordinal()][chr].call(parse_state);

            FiniteAutomatonState<StateID> state = state_stack.peek();

            if (chr == -1) {
                if (state_stack.size() == 1)
                    return ;
                else
                    return null;
            }
        }
    }

    // =========================================================================================

    public static void start_sub_expr(ParseState<StateID> parse_state) {
        
    }
    
    public static void finish_sub_expr(ParseState<StateID> parse_state) {
        
    }

    public static void start_escape(ParseState<StateID> parse_state) {}
    public static void finish_escape(ParseState<StateID> parse_state) {}

    public static void start_group(ParseState<StateID> parse_state) {}

    public static void start_inline_group(ParseState<StateID> parse_state) {}
    public static void finish_inline_group(ParseState<StateID> parse_state) {}

    public static void start_ref_group(ParseState<StateID> parse_state) {}
    public static void continue_ref_group(ParseState<StateID> parse_state) {}
    public static void finish_ref_group(ParseState<StateID> parse_state) {}

    public static void start_class(ParseState<StateID> parse_state) {}
    public static void continue_class(ParseState<StateID> parse_state) {}
    public static void finish_class(ParseState<StateID> parse_state) {}

    public static void start_complement_class(ParseState<StateID> parse_state) {}
    public static void continue_complement_class(ParseState<StateID> parse_state) {}
    public static void finish_complement_class(ParseState<StateID> parse_state) {}

    public static void start_repetition(ParseState<StateID> parse_state) {}
    public static void continue_repetition(ParseState<StateID> parse_state) {}
    public static void finish_repetition(ParseState<StateID> parse_state) {}

    public static void error(ParseState<StateID> parse_state)
            throws Invalid_NFA_Description
    {
        throw new Invalid_NFA_Description();
    }
}
