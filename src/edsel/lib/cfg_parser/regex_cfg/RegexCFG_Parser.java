package edsel.lib.cfg_parser.regex_cfg;

import edsel.lib.cfg_parser.InputNotAccepted;
import edsel.lib.cfg_parser.regex_cfg.transitions.RegexStateStackEntry;
import edsel.lib.io.SeekableBuffer;

import java.util.Iterator;
import java.util.Stack;

public class RegexCFG_Parser<REDUCTION_TYPE>
{
    public REDUCTION_TYPE parse(
            RegexCFG<REDUCTION_TYPE>                            regex_cfg,
            SeekableBuffer<RegexTerminal<REDUCTION_TYPE>>       input,
            Stack<RegexStateStackEntry<REDUCTION_TYPE>>              state_stack)
            throws InputNotAccepted
    {
        state_stack.push(new RegexStateStackEntry<>(regex_cfg.root_production);

        while (true) {
            RegexTerminal<REDUCTION_TYPE> sym = input.next();

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
}
