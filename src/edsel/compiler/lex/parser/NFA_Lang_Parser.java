package edsel.compiler.lex.parser;

import edsel.compiler.lex.automaton.AutomatonNode;
import edsel.compiler.lex.automaton.NFA;
import edsel.compiler.lex.text_io.SeekableCharBuffer;

import java.util.Stack;

import static edsel.compiler.lex.parser.NFA_Lang_Parser.StateID.*;

public class NFA_Lang_Parser {

    public static class Invalid_NFA_Description extends Exception {}

    public interface TransitionFunc {
        void call(Stack<AutomatonNode<StateID>> state_stack)
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
        __SIZE__
    }

    // =========================================================================================
    
    public static AutomatonNode<StateID> SUB_EXPR_NODE = new AutomatonNode<>(SUB_EXPR_ID, true);
    public static AutomatonNode<StateID> ESCAPE_NODE = new AutomatonNode<>(StateID.ESCAPE_ID, false);
    
    public static AutomatonNode<StateID> GROUP_OPEN_NODE = new AutomatonNode<>(StateID.GROUP_OPEN_ID, false);
    public static AutomatonNode<StateID> INLINE_GROUP_NODE = new AutomatonNode<>(StateID.INLINE_GROUP_ID, false);
    public static AutomatonNode<StateID> REF_GROUP_NODE = new AutomatonNode<>(StateID.REF_GROUP_ID, false);
    public static AutomatonNode<StateID> REF_GROUP_CLOSE_NODE = new AutomatonNode<>(StateID.REF_GROUP_CLOSE_ID, false);
    
    public static AutomatonNode<StateID> CLASS_NODE = new AutomatonNode<>(StateID.CLASS_ID, false);
    public static AutomatonNode<StateID> COMPLEMENT_CLASS_NODE = new AutomatonNode<>(StateID.COMPLEMENT_CLASS_ID, false);
    public static AutomatonNode<StateID> REPETITION_OPEN_NODE = new AutomatonNode<>(StateID.REPETITION_ID, false);

    // =========================================================================================
    
    public int SUB_EXPR_STATE = SUB_EXPR_ID.ordinal();
    public int ESCAPE_STATE = ESCAPE_ID.ordinal();
    
    public int GROUP_OPEN_STATE = GROUP_OPEN_ID.ordinal();
    public int INLINE_GROUP_STATE = INLINE_GROUP_ID.ordinal();
    public int REF_GROUP_STATE = REF_GROUP_ID.ordinal();
    public int REF_GROUP_CLOSE_STATE = REF_GROUP_CLOSE_ID.ordinal();
    
    public int CLASS_STATE = CLASS_ID.ordinal();
    public int COMPLEMENT_CLASS_STATE = COMPLEMENT_CLASS_ID.ordinal();
    public int REPETITION_STATE = REPETITION_ID.ordinal();

    // =========================================================================================

    @SuppressWarnings("unchecked")
    public static TransitionFunc[][] transition_matrix = new TransitionFunc[StateID.__SIZE__.ordinal()][];

    public static int DEFAULT_CHAR = 257;
    
    {
        for (int i = 0; i < transition_matrix.length; i++) {
            //noinspection unchecked
            transition_matrix[i] = new TransitionFunc[DEFAULT_CHAR];
        }

        transition_matrix[SUB_EXPR_STATE]['.'] = NFA_Lang_Parser::finish_sub_expr;
        transition_matrix[SUB_EXPR_STATE]['\\'] = NFA_Lang_Parser::start_escape;
        transition_matrix[SUB_EXPR_STATE]['?'] = NFA_Lang_Parser::finish_sub_expr;
        transition_matrix[SUB_EXPR_STATE]['+'] = NFA_Lang_Parser::finish_sub_expr;
        transition_matrix[SUB_EXPR_STATE]['*'] = NFA_Lang_Parser::finish_sub_expr;
        transition_matrix[SUB_EXPR_STATE]['('] = NFA_Lang_Parser::start_group;
        transition_matrix[SUB_EXPR_STATE][':'] = NFA_Lang_Parser::error;
        transition_matrix[SUB_EXPR_STATE][')'] = NFA_Lang_Parser::error;
        transition_matrix[SUB_EXPR_STATE]['['] = NFA_Lang_Parser::start_class;
        transition_matrix[SUB_EXPR_STATE][']'] = NFA_Lang_Parser::error;
        transition_matrix[SUB_EXPR_STATE]['<'] = NFA_Lang_Parser::start_complement_class;
        transition_matrix[SUB_EXPR_STATE]['>'] = NFA_Lang_Parser::error;
        transition_matrix[SUB_EXPR_STATE]['{'] = NFA_Lang_Parser::start_repetition;
        transition_matrix[SUB_EXPR_STATE]['}'] = NFA_Lang_Parser::error;
        transition_matrix[SUB_EXPR_STATE][DEFAULT_CHAR] = NFA_Lang_Parser::finish_sub_expr;

        transition_matrix[ESCAPE_STATE][DEFAULT_CHAR] = NFA_Lang_Parser::finish_escape;

        transition_matrix[GROUP_OPEN_STATE]['.'] = NFA_Lang_Parser::start_inline_group;
        transition_matrix[GROUP_OPEN_STATE]['\\'] = NFA_Lang_Parser::start_escape;
        transition_matrix[GROUP_OPEN_STATE]['?'] = NFA_Lang_Parser::error;
        transition_matrix[GROUP_OPEN_STATE]['+'] = NFA_Lang_Parser::error;
        transition_matrix[GROUP_OPEN_STATE]['*'] = NFA_Lang_Parser::error;
        transition_matrix[GROUP_OPEN_STATE]['('] = NFA_Lang_Parser::start_inline_group;
        transition_matrix[GROUP_OPEN_STATE][':'] = NFA_Lang_Parser::start_ref_group;
        transition_matrix[GROUP_OPEN_STATE][')'] = NFA_Lang_Parser::finish_inline_group;
        transition_matrix[GROUP_OPEN_STATE]['['] = NFA_Lang_Parser::start_class;
        transition_matrix[GROUP_OPEN_STATE][']'] = NFA_Lang_Parser::error;
        transition_matrix[GROUP_OPEN_STATE]['<'] = NFA_Lang_Parser::start_complement_class;
        transition_matrix[GROUP_OPEN_STATE]['>'] = NFA_Lang_Parser::error;
        transition_matrix[GROUP_OPEN_STATE]['{'] = NFA_Lang_Parser::start_repetition;
        transition_matrix[GROUP_OPEN_STATE]['}'] = NFA_Lang_Parser::error;
        transition_matrix[GROUP_OPEN_STATE][DEFAULT_CHAR] = NFA_Lang_Parser::start_inline_group;

        transition_matrix[INLINE_GROUP_STATE]['.'] = NFA_Lang_Parser::start_sub_expr;
        transition_matrix[INLINE_GROUP_STATE]['\\'] = NFA_Lang_Parser::start_escape;
        transition_matrix[INLINE_GROUP_STATE]['?'] = NFA_Lang_Parser::finish_sub_expr;
        transition_matrix[INLINE_GROUP_STATE]['+'] = NFA_Lang_Parser::finish_sub_expr;
        transition_matrix[INLINE_GROUP_STATE]['*'] = NFA_Lang_Parser::finish_sub_expr;
        transition_matrix[INLINE_GROUP_STATE]['('] = NFA_Lang_Parser::start_group;
        transition_matrix[INLINE_GROUP_STATE][':'] = NFA_Lang_Parser::error;
        transition_matrix[INLINE_GROUP_STATE][')'] = NFA_Lang_Parser::finish_inline_group;
        transition_matrix[INLINE_GROUP_STATE]['['] = NFA_Lang_Parser::start_class;
        transition_matrix[INLINE_GROUP_STATE][']'] = NFA_Lang_Parser::error;
        transition_matrix[INLINE_GROUP_STATE]['<'] = NFA_Lang_Parser::start_complement_class;
        transition_matrix[INLINE_GROUP_STATE]['>'] = NFA_Lang_Parser::error;
        transition_matrix[INLINE_GROUP_STATE]['{'] = NFA_Lang_Parser::error;
        transition_matrix[INLINE_GROUP_STATE]['}'] = NFA_Lang_Parser::error;
        transition_matrix[INLINE_GROUP_STATE][DEFAULT_CHAR] = NFA_Lang_Parser::start_sub_expr;

        transition_matrix[REF_GROUP_STATE]['.'] = NFA_Lang_Parser::error;
        transition_matrix[REF_GROUP_STATE]['\\'] = NFA_Lang_Parser::error;
        transition_matrix[REF_GROUP_STATE]['?'] = NFA_Lang_Parser::error;
        transition_matrix[REF_GROUP_STATE]['+'] = NFA_Lang_Parser::error;
        transition_matrix[REF_GROUP_STATE]['*'] = NFA_Lang_Parser::error;
        transition_matrix[REF_GROUP_STATE]['('] = NFA_Lang_Parser::error;
        transition_matrix[REF_GROUP_STATE][':'] = NFA_Lang_Parser::finish_ref_group;
        transition_matrix[REF_GROUP_STATE][')'] = NFA_Lang_Parser::error;
        transition_matrix[REF_GROUP_STATE]['['] = NFA_Lang_Parser::error;
        transition_matrix[REF_GROUP_STATE][']'] = NFA_Lang_Parser::error;
        transition_matrix[REF_GROUP_STATE]['<'] = NFA_Lang_Parser::error;
        transition_matrix[REF_GROUP_STATE]['>'] = NFA_Lang_Parser::error;
        transition_matrix[REF_GROUP_STATE]['{'] = NFA_Lang_Parser::error;
        transition_matrix[REF_GROUP_STATE]['}'] = NFA_Lang_Parser::error;
        transition_matrix[REF_GROUP_STATE][DEFAULT_CHAR] = NFA_Lang_Parser::continue_ref_group;

        transition_matrix[REF_GROUP_CLOSE_STATE]['.'] = NFA_Lang_Parser::error;
        transition_matrix[REF_GROUP_CLOSE_STATE]['\\'] = NFA_Lang_Parser::error;
        transition_matrix[REF_GROUP_CLOSE_STATE]['?'] = NFA_Lang_Parser::error;
        transition_matrix[REF_GROUP_CLOSE_STATE]['+'] = NFA_Lang_Parser::error;
        transition_matrix[REF_GROUP_CLOSE_STATE]['*'] = NFA_Lang_Parser::error;
        transition_matrix[REF_GROUP_CLOSE_STATE]['('] = NFA_Lang_Parser::error;
        transition_matrix[REF_GROUP_CLOSE_STATE][':'] = NFA_Lang_Parser::error;
        transition_matrix[REF_GROUP_CLOSE_STATE][')'] = NFA_Lang_Parser::finish_ref_group;
        transition_matrix[REF_GROUP_CLOSE_STATE]['['] = NFA_Lang_Parser::error;
        transition_matrix[REF_GROUP_CLOSE_STATE][']'] = NFA_Lang_Parser::error;
        transition_matrix[REF_GROUP_CLOSE_STATE]['<'] = NFA_Lang_Parser::error;
        transition_matrix[REF_GROUP_CLOSE_STATE]['>'] = NFA_Lang_Parser::error;
        transition_matrix[REF_GROUP_CLOSE_STATE]['{'] = NFA_Lang_Parser::error;
        transition_matrix[REF_GROUP_CLOSE_STATE]['}'] = NFA_Lang_Parser::error;
        transition_matrix[REF_GROUP_CLOSE_STATE][DEFAULT_CHAR] = NFA_Lang_Parser::error;

        transition_matrix[CLASS_STATE]['.'] = NFA_Lang_Parser::error;
        transition_matrix[CLASS_STATE]['\\'] = NFA_Lang_Parser::start_escape;
        transition_matrix[CLASS_STATE]['?'] = NFA_Lang_Parser::error;
        transition_matrix[CLASS_STATE]['+'] = NFA_Lang_Parser::error;
        transition_matrix[CLASS_STATE]['*'] = NFA_Lang_Parser::error;
        transition_matrix[CLASS_STATE]['('] = NFA_Lang_Parser::error;
        transition_matrix[CLASS_STATE][':'] = NFA_Lang_Parser::error;
        transition_matrix[CLASS_STATE][')'] = NFA_Lang_Parser::error;
        transition_matrix[CLASS_STATE]['['] = NFA_Lang_Parser::error;
        transition_matrix[CLASS_STATE][']'] = NFA_Lang_Parser::finish_class;
        transition_matrix[CLASS_STATE]['<'] = NFA_Lang_Parser::error;
        transition_matrix[CLASS_STATE]['>'] = NFA_Lang_Parser::error;
        transition_matrix[CLASS_STATE]['{'] = NFA_Lang_Parser::error;
        transition_matrix[CLASS_STATE]['}'] = NFA_Lang_Parser::error;
        transition_matrix[CLASS_STATE][DEFAULT_CHAR] = NFA_Lang_Parser::continue_class;

        transition_matrix[COMPLEMENT_CLASS_STATE]['.'] = NFA_Lang_Parser::error;
        transition_matrix[COMPLEMENT_CLASS_STATE]['\\'] = NFA_Lang_Parser::start_escape;
        transition_matrix[COMPLEMENT_CLASS_STATE]['?'] = NFA_Lang_Parser::error;
        transition_matrix[COMPLEMENT_CLASS_STATE]['+'] = NFA_Lang_Parser::error;
        transition_matrix[COMPLEMENT_CLASS_STATE]['*'] = NFA_Lang_Parser::error;
        transition_matrix[COMPLEMENT_CLASS_STATE]['('] = NFA_Lang_Parser::error;
        transition_matrix[COMPLEMENT_CLASS_STATE][':'] = NFA_Lang_Parser::error;
        transition_matrix[COMPLEMENT_CLASS_STATE][')'] = NFA_Lang_Parser::error;
        transition_matrix[COMPLEMENT_CLASS_STATE]['['] = NFA_Lang_Parser::error;
        transition_matrix[COMPLEMENT_CLASS_STATE][']'] = NFA_Lang_Parser::error;
        transition_matrix[COMPLEMENT_CLASS_STATE]['<'] = NFA_Lang_Parser::error;
        transition_matrix[COMPLEMENT_CLASS_STATE]['>'] = NFA_Lang_Parser::finish_complement_class;
        transition_matrix[COMPLEMENT_CLASS_STATE]['{'] = NFA_Lang_Parser::error;
        transition_matrix[COMPLEMENT_CLASS_STATE]['}'] = NFA_Lang_Parser::error;
        transition_matrix[COMPLEMENT_CLASS_STATE][DEFAULT_CHAR] = NFA_Lang_Parser::continue_complement_class;

        transition_matrix[REPETITION_STATE]['.'] = NFA_Lang_Parser::error;
        transition_matrix[REPETITION_STATE]['\\'] = NFA_Lang_Parser::error;
        transition_matrix[REPETITION_STATE]['?'] = NFA_Lang_Parser::error;
        transition_matrix[REPETITION_STATE]['+'] = NFA_Lang_Parser::error;
        transition_matrix[REPETITION_STATE]['*'] = NFA_Lang_Parser::error;
        transition_matrix[REPETITION_STATE]['('] = NFA_Lang_Parser::error;
        transition_matrix[REPETITION_STATE][':'] = NFA_Lang_Parser::error;
        transition_matrix[REPETITION_STATE][')'] = NFA_Lang_Parser::error;
        transition_matrix[REPETITION_STATE]['['] = NFA_Lang_Parser::error;
        transition_matrix[REPETITION_STATE][']'] = NFA_Lang_Parser::error;
        transition_matrix[REPETITION_STATE]['<'] = NFA_Lang_Parser::error;
        transition_matrix[REPETITION_STATE]['>'] = NFA_Lang_Parser::error;
        transition_matrix[REPETITION_STATE]['{'] = NFA_Lang_Parser::error;
        transition_matrix[REPETITION_STATE]['}'] = NFA_Lang_Parser::finish_repetition;
        transition_matrix[REPETITION_STATE][DEFAULT_CHAR] = NFA_Lang_Parser::error;

        transition_matrix[REPETITION_STATE]['0'] = NFA_Lang_Parser::continue_repetition;
        transition_matrix[REPETITION_STATE]['1'] = NFA_Lang_Parser::continue_repetition;
        transition_matrix[REPETITION_STATE]['2'] = NFA_Lang_Parser::continue_repetition;
        transition_matrix[REPETITION_STATE]['3'] = NFA_Lang_Parser::continue_repetition;
        transition_matrix[REPETITION_STATE]['4'] = NFA_Lang_Parser::continue_repetition;
        transition_matrix[REPETITION_STATE]['5'] = NFA_Lang_Parser::continue_repetition;
        transition_matrix[REPETITION_STATE]['6'] = NFA_Lang_Parser::continue_repetition;
        transition_matrix[REPETITION_STATE]['7'] = NFA_Lang_Parser::continue_repetition;
        transition_matrix[REPETITION_STATE]['8'] = NFA_Lang_Parser::continue_repetition;
        transition_matrix[REPETITION_STATE]['9'] = NFA_Lang_Parser::continue_repetition;
    }

    // =========================================================================================

    public static <U> NFA<U> parse_dfa_description(SeekableCharBuffer expr)
            throws Invalid_NFA_Description
    {
        Stack<AutomatonNode<StateID>> state_stack = new Stack<>();
        
        state_stack.push(SUB_EXPR_NODE);

        while (true) {
            int chr = expr.next();
            
            transition_matrix[state_stack.peek()][chr].call(state_stack);

            AutomatonNode<StateID> state = state_stack.peek();

            if (chr == -1) {
                if (state_stack.size() == 1)
                    return ;
                else
                    return null;
            }
        }
    }

    // =========================================================================================

    public static void start_sub_expr(Stack<AutomatonNode<StateID>> state_stack) {}
    public static void finish_sub_expr(Stack<AutomatonNode<StateID>> state_stack) {}

    public static void start_escape(Stack<AutomatonNode<StateID>> state_stack) {}
    public static void finish_escape(Stack<AutomatonNode<StateID>> state_stack) {}

    public static void start_group(Stack<AutomatonNode<StateID>> state_stack) {}

    public static void start_inline_group(Stack<AutomatonNode<StateID>> state_stack) {}
    public static void finish_inline_group(Stack<AutomatonNode<StateID>> state_stack) {}

    public static void start_ref_group(Stack<AutomatonNode<StateID>> state_stack) {}
    public static void continue_ref_group(Stack<AutomatonNode<StateID>> state_stack) {}
    public static void finish_ref_group(Stack<AutomatonNode<StateID>> state_stack) {}

    public static void start_class(Stack<AutomatonNode<StateID>> state_stack) {}
    public static void continue_class(Stack<AutomatonNode<StateID>> state_stack) {}
    public static void finish_class(Stack<AutomatonNode<StateID>> state_stack) {}

    public static void start_complement_class(Stack<AutomatonNode<StateID>> state_stack) {}
    public static void continue_complement_class(Stack<AutomatonNode<StateID>> state_stack) {}
    public static void finish_complement_class(Stack<AutomatonNode<StateID>> state_stack) {}

    public static void start_repetition(Stack<AutomatonNode<StateID>> state_stack) {}
    public static void continue_repetition(Stack<AutomatonNode<StateID>> state_stack) {}
    public static void finish_repetition(Stack<AutomatonNode<StateID>> state_stack) {}

    public static void error(Stack<AutomatonNode<StateID>> state_stack)
            throws Invalid_NFA_Description
    {
        throw new Invalid_NFA_Description();
    }
}
