package edsel.lib.cfg_parser.lalr;

import edsel.lib.cfg_model.CFG;
import edsel.lib.cfg_model.CFG_Production;
import edsel.lib.cfg_model.CFG_Symbol;
import edsel.lib.cfg_model.CFG_Terminal;
import edsel.lib.cfg_parser.non_deterministic.CFG_NonDetParser;
import edsel.lib.cfg_parser.reductions.CFG_DetReduction;
import edsel.lib.io.SeekableBuffer;

import java.util.Iterator;
import java.util.LinkedList;

public class LALR_Parser
        <ENUM_TERMINAL_ID extends Enum<ENUM_TERMINAL_ID>,
                TERMINAL_VALUE_TYPE,
                ENUM_PRODUCTION_ID extends  Enum<ENUM_PRODUCTION_ID>>
{
    public static class InvalidCFG_Input extends Exception {}

    public CFG_DetReduction<ENUM_TERMINAL_ID, TERMINAL_VALUE_TYPE, ENUM_PRODUCTION_ID>
    shift(LinkedList<CFG_DetReduction
            <ENUM_TERMINAL_ID,
                    TERMINAL_VALUE_TYPE,
                    ENUM_PRODUCTION_ID>>
                  sub_reductions)
    {

    }

    public
    CFG_DetReduction<ENUM_TERMINAL_ID, TERMINAL_VALUE_TYPE, ENUM_PRODUCTION_ID>
    reduce(LinkedList
                   <CFG_DetReduction
                           <ENUM_TERMINAL_ID,
                                   TERMINAL_VALUE_TYPE,
                                   ENUM_PRODUCTION_ID>>
                   sub_reductions)
    {

    }

    public CFG_DetReduction<ENUM_TERMINAL_ID,
            TERMINAL_VALUE_TYPE,
            ENUM_PRODUCTION_ID>
    parse(CFG
                  <ENUM_TERMINAL_ID,
                          TERMINAL_VALUE_TYPE,
                          ENUM_PRODUCTION_ID>               lalr_cfg,
          SeekableBuffer<CFG_Terminal
                  <ENUM_TERMINAL_ID,
                          TERMINAL_VALUE_TYPE,
                          ENUM_PRODUCTION_ID>>              input,
          LinkedList<CFG_Symbol
                  <ENUM_TERMINAL_ID,
                          TERMINAL_VALUE_TYPE,
                          ENUM_PRODUCTION_ID>>              state_stack)
            throws CFG_NonDetParser.InvalidCFG_Input
    {
        while (true) {
            CFG_Terminal
                    <ENUM_TERMINAL_ID,
                            TERMINAL_VALUE_TYPE,
                            ENUM_PRODUCTION_ID>
                    sym = input.next();

            if (sym == lalr_cfg.eof) {
                if (state_stack.peek() == lalr_cfg.root_production)
                    return;
                else
                    throw new CFG_NonDetParser.InvalidCFG_Input();
            }

            Iterator<CFG_Production
                    <ENUM_TERMINAL_ID, TERMINAL_VALUE_TYPE, ENUM_PRODUCTION_ID>>
                    production_iter = cfg.productions.iterator();

            while (production_iter.hasNext()) {

                CFG_Production<ENUM_TERMINAL_ID, TERMINAL_VALUE_TYPE, ENUM_PRODUCTION_ID>
                        cur_production = production_iter.next();

                if (cur_production.start_terminals.contains(sym))
                    return parse(cur_production, input, output_reduction);
                else
                    cur_
            }
        }
    }
}
