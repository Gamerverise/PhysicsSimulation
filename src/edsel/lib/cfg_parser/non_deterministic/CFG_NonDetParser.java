package edsel.lib.cfg_parser.non_deterministic;

import edsel.lib.cfg_model.CFG;
import edsel.lib.cfg_model.CFG_Symbol;
import edsel.lib.cfg_model.CFG_Terminal;
import edsel.lib.cfg_model.RCFG_Production;
import edsel.lib.cfg_parser.*;
import edsel.lib.io.CFG_TerminalBuffer;
import lib.java_lang_extensions.tuples.Range_int;

//public enum TransitionType {
//    SHIFT,
//    REDUCE,
//    SHIFT_OR_REDUCE,
//    NOT_APPLICABLE
//}

public class CFG_NonDetParser
        <ENUM_TERMINAL_ID extends Enum<ENUM_TERMINAL_ID>,
                ENUM_PRODUCTION_ID extends Enum<ENUM_PRODUCTION_ID>,
                REDUCTION_TYPE>
{
    public static class AmbiguousParserInput extends Exception {}
    
    @SuppressWarnings("unchecked")
    public REDUCTION_TYPE
    parse_recursive(
            RCFG_Production<ENUM_TERMINAL_ID, Range_int, ENUM_PRODUCTION_ID, REDUCTION_TYPE>
                    production,
            CFG_TerminalBuffer<ENUM_TERMINAL_ID>
                    input,
            CFG_Terminal<ENUM_TERMINAL_ID, Range_int>
                    eof
    )
            throws AmbiguousParserInput
    {

        REDUCTION_TYPE reduction = null;

        for (int i = 0; i < production.rhs.length; i++) {
            CFG_Symbol[] cur_branch = production.rhs[i];

            REDUCTION_TYPE[] sub_reductions = (REDUCTION_TYPE[]) new Object[cur_branch.length];

            for (int j = 0; j < cur_branch.length; j++) {
                CFG_Symbol cur_expected_symbol = cur_branch[j];

                REDUCTION_TYPE tmp_reduction = null;

                if (cur_expected_symbol instanceof RCFG_Production) {
                    RCFG_Production rhs_production = (RCFG_Production) cur_expected_symbol;

                    tmp_reduction = parse_recursive(rhs_production, input, eof);

                    if (tmp_reduction != null) {
                        if (reduction == null)
                            reduction = tmp_reduction;
                        else
                            throw new AmbiguousParserInput();
                    }
                } else if (cur_expected_symbol instanceof CFG_Terminal) {
                    CFG_Terminal<ENUM_TERMINAL_ID, Range_int> next_input;

                    if (input.not_empty())
                        next_input = input.peek();
                    else
                        return null;

                    CFG_Terminal<ENUM_TERMINAL_ID, Range_int>
                            cur_expected_terminal
                            =
                            (CFG_Terminal<ENUM_TERMINAL_ID, Range_int>) cur_expected_symbol;

                    if (next_input != cur_expected_terminal)
                        return null;
                    else {
                        tmp_reduction = production.reduce(next_input);
                        input.next();
                    }
                }

                sub_reductions[i] = tmp_reduction;
            }

            return production.reduce(production.rhs[i], sub_reductions);
        }

        return null;
    }

    // =========================================================================================

//    public LinkedList<Character>
//    parse_iterative(
//            RegexCFG                            regex_cfg,
//            SeekableBuffer<RegexCFG_Terminal<ENUM_TERMINAL_ID, Range_int>>       input,
//            Stack<ProductionBranchEntry>               state_stack
//    )
//            throws InputNotAccepted
//    {
//        ProductionBranchEntry<Production, ENUM_PRODUCTION_ID, REDUCTION_TYPE>
//                start_state = new ProductionBranchEntry<RegexProduction, RegexProductionID, LinkedList<Character>>
//                (START, regex_cfg.root_production, 0, 0);
//
//        state_stack.push(new ProductionBranchEntry<>(regex_cfg.root_production);
//
//        while (true) {
//            RegexCFG_Terminal<ENUM_TERMINAL_ID, Range_int><REDUCTION_TYPE> sym = input.next();
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
