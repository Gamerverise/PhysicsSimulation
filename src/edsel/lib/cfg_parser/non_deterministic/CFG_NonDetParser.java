package edsel.lib.cfg_parser.non_deterministic;

import edsel.cfgs.regex_cfg.RegexCFG;
import edsel.cfgs.regex_cfg.RegexProduction;
import edsel.cfgs.regex_cfg.RegexTerminal;
import edsel.lib.cfg_model.RCFG_Production;
import edsel.lib.cfg_parser.InputNotAccepted;
import edsel.lib.cfg_parser.ProductionBranchEntry;
import edsel.lib.cfg_parser.ParseStateEntry;
import edsel.lib.io.SeekableBuffer;

//public enum TransitionType {
//    SHIFT,
//    REDUCE,
//    SHIFT_OR_REDUCE,
//    NOT_APPLICABLE
//}

public class CFG_NonDetParser
        <PRODUCTION_TYPE extends RCFG_Production<ENUM_PRODUCTION_ID, REDUCTION_TYPE>,
                ENUM_PRODUCTION_ID extends Enum<ENUM_PRODUCTION_ID>,
                REDUCTION_TYPE>
{
    public REDUCTION_TYPE
    parse_recursive(
            RegexCFG                            regex_cfg,
            SeekableBuffer<RegexTerminal>       input,
            ParseStateEntry state_entry
    )
            throws InputNotAccepted
    {
        if (state_entry instanceof ProductionBranchEntry) {
            ProductionBranchEntry transition = (ProductionBranchEntry) state_entry;

            for (int i = 0; i < transition.production.rhs.length; i++)
                parse_recursive(regex_cfg, input, new Red);


        while (true) {
            RegexTerminal sym = input.next();

            if (sym == regex_cfg.eof) {
                if (state_stack.empty())
                    return;
                else
                    throw new InputNotAccepted();
            }

            Iterator<RegexProduction<REDUCTION_TYPE>> production_iter = regex_cfg.productions.iterator();

            while (production_iter.hasNext()) {

                RegexProduction<REDUCTION_TYPE> cur_production = production_iter.next();

                if (cur_production.start_terminals.contains(sym))
                    return parse(cur_production, input, output_reduction);
                else
                    cur_
            }
        }
    }

    // =========================================================================================

//    public LinkedList<Character>
//    parse_iterative(
//            RegexCFG                            regex_cfg,
//            SeekableBuffer<RegexTerminal>       input,
//            Stack<ProductionBranchEntry>               state_stack
//    )
//            throws InputNotAccepted
//    {
//        ProductionBranchEntry<PRODUCTION_TYPE, ENUM_PRODUCTION_ID, REDUCTION_TYPE>
//                start_state = new ProductionBranchEntry<RegexProduction, RegexProductionID, LinkedList<Character>>
//                (START, regex_cfg.root_production, 0, 0);
//
//        state_stack.push(new ProductionBranchEntry<>(regex_cfg.root_production);
//
//        while (true) {
//            RegexTerminal<REDUCTION_TYPE> sym = input.next();
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
