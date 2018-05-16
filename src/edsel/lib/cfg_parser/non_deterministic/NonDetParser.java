package edsel.lib.cfg_parser.non_deterministic;

import edsel.lib.cfg_model.*;
import edsel.lib.cfg_parser.parse_node.ParseNode;
import edsel.lib.cfg_parser.parse_node.ReductionParseNode;
import edsel.lib.cfg_parser.parse_node.TokenParseNode;
import edsel.lib.io.ParseNodeBuffer;
import edsel.lib.io.ParseNodeBuffer.ParseNodeBufferString;

public class NonDetParser
        <ENUM_PRODUCTION_ID extends Enum<ENUM_PRODUCTION_ID>,
                ENUM_TERMINAL_ID extends Enum<ENUM_TERMINAL_ID>,
                TOKEN_VALUE_TYPE> {
    public static class ParsingException extends Exception {}

    public static class AmbiguousParserInput extends ParsingException {}

    public static class InvalidProductionRestriction extends ParsingException {}

    public ReductionParseNode<ENUM_PRODUCTION_ID>
    parse_recursive(
            RCFG<ENUM_PRODUCTION_ID, ? extends RCFG_Production<ENUM_PRODUCTION_ID>>   rcfg,
            ParseNodeBuffer                                                 input
    )
            throws AmbiguousParserInput
    {
        return parse_recursive(
                rcfg,
                rcfg.start_production,
                input,
                0);
    }

    @SuppressWarnings("unchecked")
    public ReductionParseNode<ENUM_PRODUCTION_ID>
    parse_recursive(
            RCFG rcfg,
            RCFG_Production<ENUM_PRODUCTION_ID> production,
            ParseNodeBuffer input,
            int num_branches_explored
    )
            throws AmbiguousParserInput
    {
        ReductionParseNode<ENUM_PRODUCTION_ID> reduction = null;

        save_input();

        branch_loop:
        for (int i = 0; i < production.rhs.length; i++) {

            CFG_Symbol[] cur_branch = production.rhs[i];

            ParseNode[] sub_reductions = new ParseNode[cur_branch.length];

            for (int j = 0; j < cur_branch.length; j++) {

                CFG_Symbol cur_expected_symbol = cur_branch[j];
                ParseNode next_input = input.peek();

                if (next_input == null)
                    continue branch_loop;

                if (cur_expected_symbol instanceof RCFG_Production) {

                    ReductionParseNode<ENUM_PRODUCTION_ID> cur_sub_reduction;

                    if (next_input instanceof TokenParseNode)
                        continue branch_loop;

                    if (next_input instanceof ReductionParseNode) {

                        ENUM_PRODUCTION_ID cur_expected_production_id
                                = ((RCFG_Production<ENUM_PRODUCTION_ID>) cur_expected_symbol).id;

                        ENUM_PRODUCTION_ID cur_production_id
                                = ((ReductionParseNode<ENUM_PRODUCTION_ID>) next_input).production_id;

                        cur_sub_reduction = (ReductionParseNode<ENUM_PRODUCTION_ID>) next_input;

                        if (cur_sub_reduction.production_id == cur_expected_symbol.id) {
                            sub_reductions[j] = cur_sub_reduction;
                            input.advance();
                        } else
                            continue branch_loop;
                    } else {
                        RCFG_Production<ENUM_PRODUCTION_ID>
                                rhs_production = (RCFG_Production<ENUM_PRODUCTION_ID>) cur_expected_symbol;

                        cur_sub_reduction
                                = parse_recursive(rcfg, rhs_production, input, num_branches_explored + 1);

                        if (cur_sub_reduction != null) {
                            sub_reductions[j] = cur_sub_reduction;
                            input.advance();
                        } else
                            continue branch_loop;
                    }
                } else if (cur_expected_symbol instanceof RCFG_Terminal) {

                    if (next_input instanceof ReductionParseNode)
                        continue branch_loop;

                    RCFG_Terminal<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE> cur_expected_terminal
                            = (RCFG_Terminal<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE>) cur_expected_symbol;

                    TokenParseNode<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE> cur_terminal
                            = (TokenParseNode<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE>) next_input;

                    if (cur_terminal.id == cur_expected_terminal.id) {
                        cur_expected_terminal.reduce(cur_terminal);
                        sub_reductions[j] = next_input;
                        input.advance();
                    } else
                        continue branch_loop;
                }
            }

            if (reduction != null)
                throw new AmbiguousParserInput();

            int src_text_start = sub_reductions[0].src_string.src_start;
            int src_text_end = sub_reductions[sub_reductions.length - 1].src_string.src_end;
            ParseNodeBufferString string = input.get_buffer_string(src_text_start, src_text_end);

            reduction = production.reduce(i, sub_reductions, num_branches_explored, string);
        }

        if (reduction == null)
            restore_input();

        return reduction;
    }

    // =========================================================================================

    void save_input() {
        assert false;
    }

    void restore_input() {
        assert false;
    }
}
