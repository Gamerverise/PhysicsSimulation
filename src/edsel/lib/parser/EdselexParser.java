package edsel.lib.parser;

import edsel.lib.lex.automaton.FiniteAutomaton;
import edsel.lib.lex.automaton.FiniteAutomatonState;
import edsel.lib.io.SeekableCharArrayBuffer;

import java.util.Stack;

import static edsel.lib.parser.EdselexParser.StateID.*;

public abstract class EdselexParser<REDUCTION> {

    public static class Invalid_NFA_Description extends Exception {}

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

    public static class ParseState<ID_TYPE, REDUCTION> {

        public SeekableCharArrayBuffer input;
        public Stack<FiniteAutomatonState<ID_TYPE, REDUCTION>> state_stack = new Stack<>();
        public FiniteAutomaton<ID_TYPE, REDUCTION> nfa = new FiniteAutomaton<>();

        public ParseState(SeekableCharArrayBuffer input) {
            this.input = input;
        }
    }

    // =========================================================================================

    public interface TransitionFunc<REDUCTION> {
        void call(ParseState<StateID, REDUCTION> parse_state)
                throws Invalid_NFA_Description;
    }

    public FiniteAutomatonState<StateID, REDUCTION> SUB_EXPR_STATE = new FiniteAutomatonState<>(SUB_EXPR_ID, true);
    public FiniteAutomatonState<StateID, REDUCTION> ESCAPE_STATE = new FiniteAutomatonState<>(StateID.ESCAPE_ID, false);
    
    public FiniteAutomatonState<StateID, REDUCTION> GROUP_OPEN_STATE = new FiniteAutomatonState<>(StateID.GROUP_OPEN_ID, false);
    public FiniteAutomatonState<StateID, REDUCTION> INLINE_GROUP_STATE = new FiniteAutomatonState<>(StateID.INLINE_GROUP_ID, false);
    public FiniteAutomatonState<StateID, REDUCTION> REF_GROUP_STATE = new FiniteAutomatonState<>(StateID.REF_GROUP_ID, false);
    public FiniteAutomatonState<StateID, REDUCTION> REF_GROUP_CLOSE_STATE = new FiniteAutomatonState<>(StateID.REF_GROUP_CLOSE_ID, false);
    
    public FiniteAutomatonState<StateID, REDUCTION> CLASS_STATE = new FiniteAutomatonState<>(StateID.CLASS_ID, false);
    public FiniteAutomatonState<StateID, REDUCTION> COMPLEMENT_CLASS_STATE = new FiniteAutomatonState<>(StateID.COMPLEMENT_CLASS_ID, false);
    public FiniteAutomatonState<StateID, REDUCTION> REPETITION_STATE = new FiniteAutomatonState<>(StateID.REPETITION_ID, false);

    // =========================================================================================
    
    public static int star = 257;

    @SuppressWarnings("unchecked")
    public TransitionFunc[][] transition_matrix = new TransitionFunc[StateID.__SIZE__.ordinal()][];

    public void set_transition(FiniteAutomatonState<StateID, REDUCTION> state, int chr, TransitionFunc tf) {
        transition_matrix[state.id.ordinal()][chr] = tf;
    }

    static {
        for (int i = 0; i < transition_matrix.length; i++) {
            //noinspection unchecked
            transition_matrix[i] = new TransitionFunc[star];
        }

        set_transition(SUB_EXPR_STATE, '.', this::finish_sub_expr);
        set_transition(SUB_EXPR_STATE, '\\', this::start_escape);
        set_transition(SUB_EXPR_STATE, '?', this::finish_sub_expr);
        set_transition(SUB_EXPR_STATE, '+', this::finish_sub_expr);
        set_transition(SUB_EXPR_STATE, '*', this::finish_sub_expr);
        set_transition(SUB_EXPR_STATE, '(', this::start_group);
        set_transition(SUB_EXPR_STATE, ':', this::error);
        set_transition(SUB_EXPR_STATE, ')', this::error);
        set_transition(SUB_EXPR_STATE, '[', this::start_class);
        set_transition(SUB_EXPR_STATE, ']', this::error);
        set_transition(SUB_EXPR_STATE, '<', this::start_complement_class);
        set_transition(SUB_EXPR_STATE, '>', this::error);
        set_transition(SUB_EXPR_STATE, '{', this::start_repetition);
        set_transition(SUB_EXPR_STATE, '}', this::error);
        set_transition(SUB_EXPR_STATE, star, this::finish_sub_expr);

        set_transition(ESCAPE_STATE, star, this::finish_escape);

        set_transition(GROUP_OPEN_STATE, '.', this::start_inline_group);
        set_transition(GROUP_OPEN_STATE, '\\', this::start_escape);
        set_transition(GROUP_OPEN_STATE, '?', this::error);
        set_transition(GROUP_OPEN_STATE, '+', this::error);
        set_transition(GROUP_OPEN_STATE, '*', this::error);
        set_transition(GROUP_OPEN_STATE, '(', this::start_inline_group);
        set_transition(GROUP_OPEN_STATE, ':', this::start_ref_group);
        set_transition(GROUP_OPEN_STATE, ')', this::finish_inline_group);
        set_transition(GROUP_OPEN_STATE, '[', this::start_class);
        set_transition(GROUP_OPEN_STATE, ']', this::error);
        set_transition(GROUP_OPEN_STATE, '<', this::start_complement_class);
        set_transition(GROUP_OPEN_STATE, '>', this::error);
        set_transition(GROUP_OPEN_STATE, '{', this::start_repetition);
        set_transition(GROUP_OPEN_STATE, '}', this::error);
        set_transition(GROUP_OPEN_STATE, star, this::start_inline_group);

        set_transition(INLINE_GROUP_STATE, '.', this::start_sub_expr);
        set_transition(INLINE_GROUP_STATE, '\\', this::start_escape);
        set_transition(INLINE_GROUP_STATE, '?', this::finish_sub_expr);
        set_transition(INLINE_GROUP_STATE, '+', this::finish_sub_expr);
        set_transition(INLINE_GROUP_STATE, '*', this::finish_sub_expr);
        set_transition(INLINE_GROUP_STATE, '(', this::start_group);
        set_transition(INLINE_GROUP_STATE, ':', this::error);
        set_transition(INLINE_GROUP_STATE, ')', this::finish_inline_group);
        set_transition(INLINE_GROUP_STATE, '[', this::start_class);
        set_transition(INLINE_GROUP_STATE, ']', this::error);
        set_transition(INLINE_GROUP_STATE, '<', this::start_complement_class);
        set_transition(INLINE_GROUP_STATE, '>', this::error);
        set_transition(INLINE_GROUP_STATE, '{', this::error);
        set_transition(INLINE_GROUP_STATE, '}', this::error);
        set_transition(INLINE_GROUP_STATE, star, this::start_sub_expr);

        set_transition(REF_GROUP_STATE, '.', this::error);
        set_transition(REF_GROUP_STATE, '\\', this::error);
        set_transition(REF_GROUP_STATE, '?', this::error);
        set_transition(REF_GROUP_STATE, '+', this::error);
        set_transition(REF_GROUP_STATE, '*', this::error);
        set_transition(REF_GROUP_STATE, '(', this::error);
        set_transition(REF_GROUP_STATE, ':', this::finish_ref_group);
        set_transition(REF_GROUP_STATE, ')', this::error);
        set_transition(REF_GROUP_STATE, '[', this::error);
        set_transition(REF_GROUP_STATE, ']', this::error);
        set_transition(REF_GROUP_STATE, '<', this::error);
        set_transition(REF_GROUP_STATE, '>', this::error);
        set_transition(REF_GROUP_STATE, '{', this::error);
        set_transition(REF_GROUP_STATE, '}', this::error);
        set_transition(REF_GROUP_STATE, star, this::continue_ref_group);

        set_transition(REF_GROUP_CLOSE_STATE, '.', this::error);
        set_transition(REF_GROUP_CLOSE_STATE, '\\', this::error);
        set_transition(REF_GROUP_CLOSE_STATE, '?', this::error);
        set_transition(REF_GROUP_CLOSE_STATE, '+', this::error);
        set_transition(REF_GROUP_CLOSE_STATE, '*', this::error);
        set_transition(REF_GROUP_CLOSE_STATE, '(', this::error);
        set_transition(REF_GROUP_CLOSE_STATE, ':', this::error);
        set_transition(REF_GROUP_CLOSE_STATE, ')', this::finish_ref_group);
        set_transition(REF_GROUP_CLOSE_STATE, '[', this::error);
        set_transition(REF_GROUP_CLOSE_STATE, ']', this::error);
        set_transition(REF_GROUP_CLOSE_STATE, '<', this::error);
        set_transition(REF_GROUP_CLOSE_STATE, '>', this::error);
        set_transition(REF_GROUP_CLOSE_STATE, '{', this::error);
        set_transition(REF_GROUP_CLOSE_STATE, '}', this::error);
        set_transition(REF_GROUP_CLOSE_STATE, star, this::error);

        set_transition(CLASS_STATE, '.', this::error);
        set_transition(CLASS_STATE, '\\', this::start_escape);
        set_transition(CLASS_STATE, '?', this::error);
        set_transition(CLASS_STATE, '+', this::error);
        set_transition(CLASS_STATE, '*', this::error);
        set_transition(CLASS_STATE, '(', this::error);
        set_transition(CLASS_STATE, ':', this::error);
        set_transition(CLASS_STATE, ')', this::error);
        set_transition(CLASS_STATE, '[', this::error);
        set_transition(CLASS_STATE, ']', this::finish_class);
        set_transition(CLASS_STATE, '<', this::error);
        set_transition(CLASS_STATE, '>', this::error);
        set_transition(CLASS_STATE, '{', this::error);
        set_transition(CLASS_STATE, '}', this::error);
        set_transition(CLASS_STATE, star, this::continue_class);

        set_transition(COMPLEMENT_CLASS_STATE, '.', this::error);
        set_transition(COMPLEMENT_CLASS_STATE, '\\', this::start_escape);
        set_transition(COMPLEMENT_CLASS_STATE, '?', this::error);
        set_transition(COMPLEMENT_CLASS_STATE, '+', this::error);
        set_transition(COMPLEMENT_CLASS_STATE, '*', this::error);
        set_transition(COMPLEMENT_CLASS_STATE, '(', this::error);
        set_transition(COMPLEMENT_CLASS_STATE, ':', this::error);
        set_transition(COMPLEMENT_CLASS_STATE, ')', this::error);
        set_transition(COMPLEMENT_CLASS_STATE, '[', this::error);
        set_transition(COMPLEMENT_CLASS_STATE, ']', this::error);
        set_transition(COMPLEMENT_CLASS_STATE, '<', this::error);
        set_transition(COMPLEMENT_CLASS_STATE, '>', this::finish_complement_class);
        set_transition(COMPLEMENT_CLASS_STATE, '{', this::error);
        set_transition(COMPLEMENT_CLASS_STATE, '}', this::error);
        set_transition(COMPLEMENT_CLASS_STATE, star, this::continue_complement_class);

        set_transition(REPETITION_STATE, '.', this::error);
        set_transition(REPETITION_STATE, '\\', this::error);
        set_transition(REPETITION_STATE, '?', this::error);
        set_transition(REPETITION_STATE, '+', this::error);
        set_transition(REPETITION_STATE, '*', this::error);
        set_transition(REPETITION_STATE, '(', this::error);
        set_transition(REPETITION_STATE, ':', this::error);
        set_transition(REPETITION_STATE, ')', this::error);
        set_transition(REPETITION_STATE, '[', this::error);
        set_transition(REPETITION_STATE, ']', this::error);
        set_transition(REPETITION_STATE, '<', this::error);
        set_transition(REPETITION_STATE, '>', this::error);
        set_transition(REPETITION_STATE, '{', this::error);
        set_transition(REPETITION_STATE, '}', this::finish_repetition);
        set_transition(REPETITION_STATE, star, this::error);

        set_transition(REPETITION_STATE, '0', this::continue_repetition);
        set_transition(REPETITION_STATE, '1', this::continue_repetition);
        set_transition(REPETITION_STATE, '2', this::continue_repetition);
        set_transition(REPETITION_STATE, '3', this::continue_repetition);
        set_transition(REPETITION_STATE, '4', this::continue_repetition);
        set_transition(REPETITION_STATE, '5', this::continue_repetition);
        set_transition(REPETITION_STATE, '6', this::continue_repetition);
        set_transition(REPETITION_STATE, '7', this::continue_repetition);
        set_transition(REPETITION_STATE, '8', this::continue_repetition);
        set_transition(REPETITION_STATE, '9', this::continue_repetition);
    }

    // =========================================================================================

    public REDUCTION parse_edselex(SeekableCharArrayBuffer expr)
            throws Invalid_NFA_Description
    {
        ParseState<StateID, REDUCTION> parse_state = new ParseState<>(expr);
        Stack<FiniteAutomatonState<StateID, REDUCTION>> state_stack = new Stack<>();

        state_stack.push(SUB_EXPR_STATE);

        while (true) {
            int chr = expr.next();

            //noinspection unchecked
            transition_matrix[state_stack.peek().id.ordinal()][chr].call(parse_state);

            FiniteAutomatonState<StateID, REDUCTION> state = state_stack.peek();

            if (chr == -1) {
                if (state_stack.size() == 1)
                    return state_stack.pop().reduction;
                else
                    return null;
            }
        }
    }

    // =========================================================================================

    public abstract void start_sub_expr(ParseState<StateID, REDUCTION> parse_state);
    public abstract void finish_sub_expr(ParseState<StateID, REDUCTION> parse_state);

    public abstract void start_escape(ParseState<StateID, REDUCTION> parse_state);
    public abstract void finish_escape(ParseState<StateID, REDUCTION> parse_state);

    public abstract void start_group(ParseState<StateID, REDUCTION> parse_state);

    public abstract void start_inline_group(ParseState<StateID, REDUCTION> parse_state);
    public abstract void finish_inline_group(ParseState<StateID, REDUCTION> parse_state);

    public abstract void start_ref_group(ParseState<StateID, REDUCTION> parse_state);
    public abstract void continue_ref_group(ParseState<StateID, REDUCTION> parse_state);
    public abstract void finish_ref_group(ParseState<StateID, REDUCTION> parse_state);

    public abstract void start_class(ParseState<StateID, REDUCTION> parse_state);
    public abstract void continue_class(ParseState<StateID, REDUCTION> parse_state);
    public abstract void finish_class(ParseState<StateID, REDUCTION> parse_state);

    public abstract void start_complement_class(ParseState<StateID, REDUCTION> parse_state);
    public abstract void continue_complement_class(ParseState<StateID, REDUCTION> parse_state);
    public abstract void finish_complement_class(ParseState<StateID, REDUCTION> parse_state);

    public abstract void start_repetition(ParseState<StateID, REDUCTION> parse_state);
    public abstract void continue_repetition(ParseState<StateID, REDUCTION> parse_state);
    public abstract void finish_repetition(ParseState<StateID, REDUCTION> parse_state);

    public void error(ParseState<StateID, REDUCTION> parse_state)
            throws Invalid_NFA_Description
    {
        throw new Invalid_NFA_Description();
    }
}
