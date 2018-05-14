package edsel.lib.cfg_parser.non_deterministic;

import edsel.lib.cfg_model.CFG_Symbol;
import edsel.lib.cfg_model.RCFG_Terminal;
import edsel.lib.cfg_model.RCFG_Production;
import edsel.lib.cfg_parser.reductions.ReductionBase;
import edsel.lib.cfg_parser.reductions.Reduction;
import edsel.lib.io.Token;
import edsel.lib.io.TokenBuffer;
import edsel.lib.io.TokenBuffer.TokenBufferString;

//public enum TransitionType {
//    SHIFT,
//    REDUCE,
//    SHIFT_OR_REDUCE,
//    NOT_APPLICABLE
//}

public class NonDetParser
        <ENUM_PRODUCTION_ID extends Enum<ENUM_PRODUCTION_ID>,
                ENUM_TERMINAL_ID extends Enum<ENUM_TERMINAL_ID>,
                TOKEN_VALUE_TYPE>
{
    public static class AmbiguousParserInput extends Exception {}
    
    @SuppressWarnings("unchecked")
    public Reduction<ENUM_PRODUCTION_ID, RCFG_Production<ENUM_PRODUCTION_ID>>
    parse_recursive(
            RCFG_Production<ENUM_PRODUCTION_ID>
                    production,

            TokenBuffer<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE>
                    input,
            int
                    num_branches_explored
    )
            throws AmbiguousParserInput
    {
        Reduction<ENUM_PRODUCTION_ID, RCFG_Production<ENUM_PRODUCTION_ID>>
                reduction = null;

        for (int i = 0; i < production.rhs.length; i++) {
            CFG_Symbol[] cur_branch = production.rhs[i];

            ReductionBase[] sub_reductions = (ReductionBase[]) new Object[cur_branch.length];

            for (int j = 0; j < cur_branch.length; j++) {
                CFG_Symbol cur_expected_symbol = cur_branch[j];

                if (cur_expected_symbol instanceof RCFG_Production) {

                    RCFG_Production<ENUM_PRODUCTION_ID> rhs_production
                            = (RCFG_Production<ENUM_PRODUCTION_ID>) cur_expected_symbol;

                    sub_reductions[j] = parse_recursive(rhs_production, input, num_branches_explored + 1);

                } else if (cur_expected_symbol instanceof RCFG_Terminal) {

                    Token<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE> next_input = input.next();

                    if (input.not_empty())
                        next_input = input.peek();
                    else
                        return null;

                    RCFG_Terminal<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE>
                            cur_expected_terminal
                            =
                            (RCFG_Terminal<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE>) cur_expected_symbol;

                    if (next_input.id != cur_expected_terminal.id)
                        return null;
                    else {
                        cur_expected_terminal.reduce(next_input);
                        sub_reductions[j] = next_input;
                    }
                }
            }

            int src_text_start = sub_reductions[0].src_string.src_start;
            int src_text_end = sub_reductions[sub_reductions.length - 1].src_string.src_end;
            TokenBufferString string = input.get_buffer_string(src_text_start, src_text_end);

            Reduction<ENUM_PRODUCTION_ID, RCFG_Production<ENUM_PRODUCTION_ID>>
                    tmp_reduction = production.reduce(i, sub_reductions, num_branches_explored, string);

            if (tmp_reduction == null)
                continue;

            if (reduction != null)
                throw new AmbiguousParserInput();

            reduction = tmp_reduction;
        }

        return reduction;
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
