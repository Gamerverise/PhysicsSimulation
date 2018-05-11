package edsel.lib.cfg_parser.lalr;

import edsel.lib.cfg_model.CFG;
import edsel.lib.cfg_model.CFG_Production;
import edsel.lib.cfg_model.CFG_Terminal;
import edsel.lib.cfg_model.RCFG_Production;
import edsel.lib.cfg_model.RCFG_Symbol;
import edsel.lib.cfg_model.RCFG_Terminal;
import edsel.lib.cfg_parser.InputNotAccepted;
import edsel.lib.cfg_parser.transitions.AbstractStateStackEntry;
import edsel.lib.io.SeekableBuffer;

import java.util.Stack;

public class LALR_Parser
        <TERMINAL_TYPE
                extends RCFG_Terminal<TERMINAL_TYPE,
                        PRODUCTION_TYPE,
                        SYMBOL_TYPE,
                        ENUM_TERMINAL_ID,
                        TERMINAL_VALUE_TYPE,
                        ENUM_PRODUCTION_ID,
                        REDUCTION_TYPE>,
                PRODUCTION_TYPE
                        extends RCFG_Production<TERMINAL_TYPE,
                                PRODUCTION_TYPE,
                                SYMBOL_TYPE,
                                ENUM_TERMINAL_ID,
                                TERMINAL_VALUE_TYPE,
                                ENUM_PRODUCTION_ID,
                                REDUCTION_TYPE>,
                SYMBOL_TYPE
                        extends RCFG_Symbol<TERMINAL_TYPE,
                                PRODUCTION_TYPE,
                                SYMBOL_TYPE,
                                ENUM_TERMINAL_ID,
                                TERMINAL_VALUE_TYPE,
                                ENUM_PRODUCTION_ID,
                                REDUCTION_TYPE>,
                ENUM_TERMINAL_ID extends Enum<ENUM_TERMINAL_ID>,
                TERMINAL_VALUE_TYPE,
                ENUM_PRODUCTION_ID extends Enum<ENUM_PRODUCTION_ID>,
                REDUCTION_TYPE>
{

    public LALR_ParseTable
            <TERMINAL_TYPE,
                    PRODUCTION_TYPE,
                    SYMBOL_TYPE,
                    ENUM_TERMINAL_ID,
                    TERMINAL_VALUE_TYPE,
                    ENUM_PRODUCTION_ID,
                    REDUCTION_TYPE>
            parse_table;

    public REDUCTION_TYPE parse(
            CFG
                    <TERMINAL_TYPE,
                            PRODUCTION_TYPE,
                            SYMBOL_TYPE,
                            ENUM_TERMINAL_ID,
                            TERMINAL_VALUE_TYPE,
                            ENUM_PRODUCTION_ID>               lalr_cfg,
            SeekableBuffer
                    <CFG_Terminal
                            <TERMINAL_TYPE,
                                    PRODUCTION_TYPE,
                                    SYMBOL_TYPE,
                                    ENUM_TERMINAL_ID,
                                    TERMINAL_VALUE_TYPE,
                                    ENUM_PRODUCTION_ID>>      input,
            Stack
                    <AbstractStateStackEntry<TERMINAL_TYPE,
                                                                            PRODUCTION_TYPE,
                                                                            SYMBOL_TYPE,
                                                                            ENUM_TERMINAL_ID,
                                                                            TERMINAL_VALUE_TYPE,
                                                                            ENUM_PRODUCTION_ID,
                                                                            REDUCTION_TYPE>>          state_stack)
            throws InputNotAccepted
    {
        while (true) {
            CFG_Terminal
                    <TERMINAL_TYPE,
                            PRODUCTION_TYPE,
                            SYMBOL_TYPE,
                            ENUM_TERMINAL_ID,
                            TERMINAL_VALUE_TYPE,
                            ENUM_PRODUCTION_ID>
                    sym = input.next();

            if (sym == lalr_cfg.eof) {
                if (state_stack.empty())
                    return;
                else
                    throw new InputNotAccepted();
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


//    public PRODUCTION_TYPE parse_lalr_naive(
//            SeekableBuffer<TERMINAL_TYPE>                       input,
//            Stack<
//                    CFG_Symbol
//                            <ENUM_TERMINAL_ID,
//                                    TERMINAL_VALUE_TYPE,
//                                    ENUM_PRODUCTION_ID>> state_stack,
//            PRODUCTION_TYPE                                     root_production)
//            throws CFG.InvalidCFG_Input
//    {
//        while (true) {
//            TERMINAL_TYPE terminal = input.next();
//
//            if (terminal == eof) {
//                if (state_stack.size() == 1)
//                    return (PRODUCTION_TYPE) state_stack.pop();
//                else
//                    throw new CFG.InvalidCFG_Input();
//            }
//
//            LALR_ParseTable.ParseTableEntry transition = parse_table.get_transition(state_stack, terminal);
//
//            if (transition instanceof LALR_ParseTable.SHIFT_TypeParseTableEntry)
//                state_stack.push(terminal);
//            else {
//                LALR_ParseTable.REDUCE_TypeParseTableEntry reduce_transition = (LALR_ParseTable.REDUCE_TypeParseTableEntry) transition;
//
//                PRODUCTION_TYPE parse_tree = new CFG_Production<>()
//            }
//        }
//    }
}
