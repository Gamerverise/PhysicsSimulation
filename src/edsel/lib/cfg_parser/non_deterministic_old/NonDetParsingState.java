package edsel.lib.cfg_parser.non_deterministic_old;

import edsel.lib.cfg_parser.CFG_Parser;
import edsel.lib.cfg_parser.ParsingState;
import edsel.lib.cfg_parser.non_deterministic.NonDetParsingStateFrame;
import lib.data_structures.list.LinkedListLegacy;

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
                                        edsel.lib.cfg_parser.non_deterministic.NonDetParsingState<ENUM_PRODUCTION_ID,
                                                        ENUM_TERMINAL_ID,
                                                        TOKEN_VALUE_TYPE,
                                                        SYMBOL_BUFFER_TYPE>>.SymbolBuffer>
        extends
        ParsingState
                <ENUM_PRODUCTION_ID,
                        ENUM_TERMINAL_ID,
                        TOKEN_VALUE_TYPE,
                        SYMBOL_BUFFER_TYPE,
                        edsel.lib.cfg_parser.non_deterministic.NonDetParsingState<ENUM_PRODUCTION_ID,
                                        ENUM_TERMINAL_ID,
                                        TOKEN_VALUE_TYPE,
                                        SYMBOL_BUFFER_TYPE>>
{
    public LinkedListLegacy<NonDetParsingStateFrame>
            frame_stack = new LinkedListLegacy<>(new NonDetParsingStateFrame());

    int num_branches_explored = 0;

    public NonDetParsingState(SYMBOL_BUFFER_TYPE input) {
        super(input);
    }

    // =========================================================================================

    public int get_prefixes_in_progress() {
        return frame_stack.peek().num_prefixes_in_progress;
    }

    public void inc_prefixes_in_progress() {
        frame_stack.peek().num_prefixes_in_progress++;
    }

    public void inc_prefixes_to_retire() {
        frame_stack.peek().num_prefixes_to_retire++;
    }

    public boolean retire_prefix() {
        NonDetParsingStateFrame top = frame_stack.peek();

        if (top.num_prefixes_in_progress > 0) {
            top.num_prefixes_in_progress--;
            return true;
        }

        return false;
    }

    // =========================================================================================

    public int get_cur_depth() {
        return frame_stack.peek().exploration_depth;
    }

    public void inc_cur_depth() {
        frame_stack.peek().exploration_depth++;
    }

    public void dec_cur_depth() {
        frame_stack.peek().exploration_depth--;
    }

    // =========================================================================================

    public edsel.lib.cfg_parser.non_deterministic.NonDetParsingState<ENUM_PRODUCTION_ID, ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE, SYMBOL_BUFFER_TYPE>
    branch()
    {
        edsel.lib.cfg_parser.non_deterministic.NonDetParsingState<ENUM_PRODUCTION_ID, ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE, SYMBOL_BUFFER_TYPE>
                result = new edsel.lib.cfg_parser.non_deterministic.NonDetParsingState<>(input);

        result.frame_stack = new LinkedListLegacy<>(new NonDetParsingStateFrame(frame_stack.peek()));

        result.num_branches_explored = num_branches_explored;

        return result;
    }

    public void accept_branch(
            edsel.lib.cfg_parser.non_deterministic.NonDetParsingState<ENUM_PRODUCTION_ID, ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE, SYMBOL_BUFFER_TYPE>
                    state)
    {
        frame_stack.append(state.frame_stack.head);
    }
}
