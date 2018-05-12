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
            CFG<ENUM_TERMINAL_ID, Range_int, ENUM_PRODUCTION_ID> regex_cfg,
            CFG_TerminalBuffer<ENUM_TERMINAL_ID> input,
            ParseStateEntry state_entry
    )
            throws InputNotAccepted, AmbiguousParserInput
    {
        if (state_entry instanceof ProductionEntry) {

            ProductionEntry<ENUM_TERMINAL_ID, ENUM_PRODUCTION_ID, REDUCTION_TYPE>
                    production_entry 
                    = (ProductionEntry<ENUM_TERMINAL_ID, ENUM_PRODUCTION_ID, REDUCTION_TYPE>)
                    state_entry;

            RCFG_Production<ENUM_PRODUCTION_ID, REDUCTION_TYPE> production = production_entry.production;

            REDUCTION_TYPE reduction = null;
            
            for (int i = 0; i < production.rhs.length; i++) {
                REDUCTION_TYPE tmp_reduction
                        = parse_recursive(
                                regex_cfg,
                                input,
                                new ProductionBranchEntry(state_entry, production, i, 0));
                
                if (tmp_reduction != null) {
                    if (reduction == null)
                        reduction = tmp_reduction;
                    else
                        throw new AmbiguousParserInput();
                }
            }
            
            if (reduction == null)
                throw new InputNotAccepted();
            
            return reduction;
            
        } else if (state_entry instanceof ProductionBranchEntry) {
            
            ProductionBranchEntry branch = (ProductionBranchEntry) state_entry;

            CFG_Symbol expected_symbol = branch.production.rhs[branch.cur_alternative][branch.cur_alternative_index];
            
            if (expected_symbol instanceof CFG_Terminal) {
                ENUM_TERMINAL_ID expected_id = ((CFG_Terminal<ENUM_TERMINAL_ID, Range_int>) expected_symbol).id;
                
                if(input.peek().id == expected_id)
                    recurse
            }
            
            CFG_Terminal<ENUM_TERMINAL_ID, Range_int> terminal;
            
            for (int i = 0; i < transition.production.rhs.length; i++)
                parse_recursive(regex_cfg, input, new Red);

        } else if (state_entry instanceof TerminalEntry) {
            
        }
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
