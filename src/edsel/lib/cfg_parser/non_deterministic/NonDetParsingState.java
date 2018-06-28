package edsel.lib.cfg_parser.non_deterministic;

import edsel.lib.cfg_parser.CFG_Parser;
import edsel.lib.cfg_parser.ParsingState;
import edsel.lib.cfg_parser.parse_node.Reduction;
import lib.data_structures.list.LinkedListLegacy;

public class NonDetParsingState
        <ENUM_PRODUCTION_ID extends Enum<ENUM_PRODUCTION_ID>,
                ENUM_TERMINAL_ID extends Enum<ENUM_TERMINAL_ID>,
                TOKEN_VALUE_TYPE>
        extends
        ParsingState
                <ENUM_PRODUCTION_ID,
                        ENUM_TERMINAL_ID,
                        TOKEN_VALUE_TYPE>
{
    public LinkedListLegacy<NonDetParsingStateFrame<ENUM_PRODUCTION_ID>>
            frame_stack = new LinkedListLegacy<>(new NonDetParsingStateFrame<>());

    int num_branches_explored = 0;

    public NonDetParsingState(
            CFG_Parser<ENUM_PRODUCTION_ID, ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE>.SymbolBuffer
                    input)
    {
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

    public void set_reduction(Reduction<ENUM_PRODUCTION_ID> reduction) {
        frame_stack.peek().reduction = reduction;
    }

    public Reduction<ENUM_PRODUCTION_ID> get_reduction() {
        return frame_stack.peek().reduction;
    }

    // =========================================================================================

    public
    NonDetParsingState<ENUM_PRODUCTION_ID, ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE>
    branch()
    {
        NonDetParsingState<ENUM_PRODUCTION_ID, ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE>
                result = new NonDetParsingState<>(input);

        result.frame_stack = new LinkedListLegacy<>(new NonDetParsingStateFrame<>(frame_stack.peek()));

        result.num_branches_explored = num_branches_explored;

        return result;
    }

    public void accept_branch(
            NonDetParsingState<ENUM_PRODUCTION_ID, ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE>
                    state)
    {
        frame_stack.append(state.frame_stack.head);
    }
}
