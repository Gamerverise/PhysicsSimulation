package edsel.lib.cfg_parser.non_deterministic;

import edsel.lib.cfg_model.*;
import edsel.lib.cfg_parser.reductions.ReductionBase;
import edsel.lib.cfg_parser.reductions.Reduction;
import edsel.lib.io.Token;
import edsel.lib.io.TokenBuffer;
import edsel.lib.io.TokenBuffer.TokenBufferString;

public class NonDetParser
        <ENUM_PRODUCTION_ID extends Enum<ENUM_PRODUCTION_ID>,
                ENUM_TERMINAL_ID extends Enum<ENUM_TERMINAL_ID>,
                TOKEN_VALUE_TYPE>
{
    public static class ParsingException extends Exception {}
    public static class AmbiguousParserInput extends ParsingException {}
    public static class InvalidProductionRestriction extends ParsingException {}

    public
    Reduction
            <ENUM_PRODUCTION_ID,
                    ENUM_TERMINAL_ID,
                    TOKEN_VALUE_TYPE>
    parse_recursive(
            RCFG
                    <ENUM_PRODUCTION_ID,
                            RCFG_Production
                                    <ENUM_PRODUCTION_ID,
                                            ENUM_TERMINAL_ID,
                                            TOKEN_VALUE_TYPE>,
                            ENUM_TERMINAL_ID,
                            TOKEN_VALUE_TYPE>
                    rcfg,
            TokenBuffer<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE>
                    input
    )
            throws AmbiguousParserInput, InvalidProductionRestriction
    {
        return parse_recursive(
                rcfg,
                rcfg.start_production,
                input,
                0,
                null,
                null,
                rcfg.restrict_id,
                rcfg.unrestrict_id,
                rcfg.gate_id);
    }

    @SuppressWarnings("unchecked")
    public
    Reduction
            <ENUM_PRODUCTION_ID,
                    ENUM_TERMINAL_ID,
                    TOKEN_VALUE_TYPE>
    parse_recursive(
            RCFG                                    rcfg,
            RCFG_Production
                    <ENUM_PRODUCTION_ID,
                            ENUM_TERMINAL_ID,
                            TOKEN_VALUE_TYPE>       production,
            TokenBuffer
                    <ENUM_TERMINAL_ID,
                            TOKEN_VALUE_TYPE>
                                                    input,
            int                                     num_branches_explored,
            ENUM_PRODUCTION_ID                      production_restriction,
            ENUM_TERMINAL_ID                        terminal_restriction,
            ENUM_TERMINAL_ID                        restrict_id,
            ENUM_TERMINAL_ID                        unrestrict_id,
            ENUM_TERMINAL_ID                        gate_id
    )
            throws AmbiguousParserInput, InvalidProductionRestriction
    {
        Reduction
                <ENUM_PRODUCTION_ID,
                        ENUM_TERMINAL_ID,
                        TOKEN_VALUE_TYPE>
                reduction = null;

        branch_loop:
        for (int i = 0; i < production.rhs.length; i++) {

            CFG_Symbol[] cur_branch = production.rhs[i];

            ReductionBase[] sub_reductions = new ReductionBase[cur_branch.length];

            save_input();

            for (int j = 0; j < cur_branch.length; j++) {

                Token<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE> next_input = input.peek();

                if (next_input.id == gate_id) {
                    input.next();
                    continue branch_loop;
                }

                CFG_Symbol cur_expected_symbol;

                if (next_input.id == restrict_id) {
                    cur_expected_symbol = rcfg.get_production_id(next_input.src_string);

                    if (cur_expected_symbol == null)
                        throw new InvalidProductionRestriction();
                }

                if (next_input.id == unrestrict_id) {
                    restriction = null;
                    continue;
                }

                CFG_Symbol cur_expected_symbol = cur_branch[j];

                if (cur_expected_symbol instanceof RCFG_Production) {

                    RCFG_Production
                            <ENUM_PRODUCTION_ID,
                                    ENUM_TERMINAL_ID,
                                    TOKEN_VALUE_TYPE>
                            rhs_production
                            =
                            (RCFG_Production
                                    <ENUM_PRODUCTION_ID,
                                            ENUM_TERMINAL_ID,
                                            TOKEN_VALUE_TYPE>)
                                    cur_expected_symbol;

                    ReductionBase cur_sub_reduction
                            =
                            parse_recursive(
                                    rcfg,
                                    rhs_production,
                                    input,
                                    num_branches_explored + 1,
                                    restrict_id,
                                    unrestrict_id,
                                    gate_id);

                    if (cur_sub_reduction == null)
                        continue branch_loop;

                    sub_reductions[j] = cur_sub_reduction;

                } else if (cur_expected_symbol instanceof RCFG_Terminal) {

                    Token<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE> next_input = input.next();

                    if (next_input == null)
                        continue branch_loop;
                    RCFG_Terminal<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE>
                            cur_expected_terminal
                            =
                            (RCFG_Terminal<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE>) cur_expected_symbol;

                    if (next_input.id != cur_expected_terminal.id)
                        continue branch_loop;
                    else {
                        cur_expected_terminal.reduce(next_input);
                        sub_reductions[j] = next_input;
                    }
                }
            }

            restore_input();

            if (reduction != null)
                throw new AmbiguousParserInput();

            int src_text_start = sub_reductions[0].src_string.src_start;
            int src_text_end = sub_reductions[sub_reductions.length - 1].src_string.src_end;
            TokenBufferString string = input.get_buffer_string(src_text_start, src_text_end);

            reduction = production.reduce(i, sub_reductions, num_branches_explored, string);
        }

        return reduction;
    }

    // =========================================================================================

    void save_input() {

    }

    void restore_input() {

    }

    // =========================================================================================

//    public LinkedList<Character>
//    parse_iterative(
//            RegexCFG                            regex_cfg,
//            SeekableBuffer<RegexCFG_Terminal<ENUM_TERMINAL_ID,>>       input,
//            Stack<ProductionBranchEntry>               state_stack
//    )
//            throws InputNotAccepted
//    {
//        ProductionBranchEntry<Production, ENUM_PRODUCTION_ID, REDUCTION_TYPE>
//                start_state = new ProductionBranchEntry<RegexProduction, RegexProductionID, LinkedList<Character>>
//                (START_ID, regex_cfg.root_production, 0, 0);
//
//        state_stack.push(new ProductionBranchEntry<>(regex_cfg.root_production);
//
//        while (true) {
//            RegexCFG_Terminal<ENUM_TERMINAL_ID, ><REDUCTION_TYPE> sym = input.next();
//
//            if (sym == regex_cfg.eof) {
//                if (state_stack.empty())
//                    return;
//                else
//                    throw new InputNotAccepted();
//            }
//
//            Iterator<RegexProduction<REDUCTION_TYPE>> production_iter = regex_cfg.productions.iterator();
//
//            while (production_iter.hasNext()) {
//
//                RegexProduction<REDUCTION_TYPE> cur_production = production_iter.next();
//
//                if (cur_production.start_terminals.contains(sym))
//                    return parse(cur_production, input, output_reduction);
//                else
//                    cur_
//            }
//        }
//    }
}
