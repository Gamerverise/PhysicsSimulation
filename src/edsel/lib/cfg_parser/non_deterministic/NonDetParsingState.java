package edsel.lib.cfg_parser.non_deterministic;

import edsel.lib.cfg_model.CFG_Symbol;
import edsel.lib.cfg_parser.CFG_Parser;
import edsel.lib.cfg_parser.ParsingState;
import lib.data_structures.list.LinkedListLegacy;

import java.util.Stack;

public class NonDetParsingState
        <ENUM_PRODUCTION_ID extends Enum<ENUM_PRODUCTION_ID>,
                ENUM_TERMINAL_ID extends Enum<ENUM_TERMINAL_ID>,
                TOKEN_VALUE_TYPE,
                SYMBOL_BUFFER_TYPE extends
                        CFG_Parser
                                <ENUM_PRODUCTION_ID,
                                        ENUM_TERMINAL_ID,
                                        TOKEN_VALUE_TYPE,
                                        SYMBOL_BUFFER_TYPE,
                                        NonDetParsingState
                                                <ENUM_PRODUCTION_ID,
                                                        ENUM_TERMINAL_ID,
                                                        TOKEN_VALUE_TYPE,
                                                        SYMBOL_BUFFER_TYPE>>.SymbolBuffer>
        extends
        ParsingState
                <ENUM_PRODUCTION_ID,
                        ENUM_TERMINAL_ID,
                        TOKEN_VALUE_TYPE,
                        SYMBOL_BUFFER_TYPE,
                        NonDetParsingState
                                <ENUM_PRODUCTION_ID,
                                        ENUM_TERMINAL_ID,
                                        TOKEN_VALUE_TYPE,
                                        SYMBOL_BUFFER_TYPE>>
{
    public Stack<NonDetParsingStateFrame> state_stack = new Stack<>();

    public LinkedListLegacy<CFG_Symbol> exploration_stack = new LinkedListLegacy<>();

    int exploration_depth = 0;

    int num_branches_explored = 0;

    public NonDetParsingState(SYMBOL_BUFFER_TYPE input) {
        super(input);

        exploration_stack.push(null);

        NonDetParsingStateFrame head_frame = new NonDetParsingStateFrame(exploration_stack.head);

        state_stack.push(head_frame);
    }

    public void record_expected_symbol(CFG_Symbol symbol) {
        exploration_stack.push(symbol);
        state_stack.peek().exploration_stack_ptr = exploration_stack.tail;
    }

    public boolean chk_consecutive_expected_production(CFG_Symbol symbol) {
        return symbol == exploration_stack.peek();
    }

    public int get_prefixes_in_progress() {
        return state_stack.peek().num_prefixes_in_progress;
    }

    public void inc_prefixes_in_progress() {
        state_stack.peek().num_prefixes_in_progress++;
    }

    public void inc_prefixes_to_retire() {
        state_stack.peek().num_prefixes_to_retire++;
    }

    public boolean retire_prefix() {
        NonDetParsingStateFrame top = state_stack.peek();

        if (top.num_prefixes_in_progress > 0) {
            top.num_prefixes_in_progress--;
            return true;
        }

        return false;
    }

    public void save() {
        input.save();

        NonDetParsingStateFrame top = state_stack.peek();
        state_stack.push(new NonDetParsingStateFrame(top));
    }

    public void restore() {
        input.restore();
        state_stack.pop();
        exploration_stack.truncate(state_stack.peek().exploration_stack_ptr);
    }
}
