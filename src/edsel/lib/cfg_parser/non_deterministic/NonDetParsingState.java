package edsel.lib.cfg_parser.non_deterministic;

import edsel.lib.cfg_parser.CFG_Parser;
import edsel.lib.cfg_parser.ParsingState;

import java.util.Stack;

public class NonDetParsingState
        <ENUM_PRODUCTION_ID extends Enum<ENUM_PRODUCTION_ID>,
                ENUM_TERMINAL_ID extends Enum<ENUM_TERMINAL_ID>,
                TOKEN_VALUE_TYPE,
                SYMBOL_BUFFER_TYPE extends
                        CFG_Parser
                                <ENUM_PRODUCTION_ID, ENUM_TERMINAL_ID,
                                        TOKEN_VALUE_TYPE,
                                        SYMBOL_BUFFER_TYPE>
                                .SymbolBuffer>
        extends
        ParsingState<ENUM_PRODUCTION_ID, ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE, SYMBOL_BUFFER_TYPE>
{
    public Stack<NonDetParser.PrefixInfo> prefix_info_stack = new Stack<>();
    int num_branches_explored = 0;

    public NonDetParsingState(SYMBOL_BUFFER_TYPE input) {
        super(input);
    }

    public void save() {
        input.save();

        NonDetParser.PrefixInfo top = prefix_info_stack.peek();
        prefix_info_stack.push(new NonDetParser.PrefixInfo(top));
    }

    public void restore() {
        input.restore();
        prefix_info_stack.pop();
    }

    public int get_prefixes_in_progress() {
        return prefix_info_stack.peek().num_prefixes_in_progress;
    }

    public void inc_prefixes_in_progress() {
        prefix_info_stack.peek().num_prefixes_in_progress++;
    }

    public void inc_prefixes_to_retire() {
        prefix_info_stack.peek().num_prefixes_to_retire++;
    }

    public boolean retire_prefix() {
        NonDetParser.PrefixInfo top = prefix_info_stack.peek();

        if (top.num_prefixes_in_progress > 0) {
            top.num_prefixes_in_progress--;
            return true;
        }

        return false;
    }
}
