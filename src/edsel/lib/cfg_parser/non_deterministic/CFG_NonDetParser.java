package edsel.lib.cfg_parser.non_deterministic;

import edsel.lib.cfg_model.CFG;
import edsel.lib.cfg_model.CFG_Reduction;
import edsel.lib.cfg_model.CFG_Terminal;
import edsel.lib.cfg_model.CFG_TerminalSet;
import edsel.lib.cfg_parser.transitions.CFG_NonDeterministicTransition;
import edsel.lib.io.SeekableBuffer;

public class CFG_NonDetParser
        <ENUM_TERMINAL_ID extends Enum<ENUM_TERMINAL_ID>,
                TERMINAL_VALUE_TYPE,
                ENUM_PRODUCTION_ID extends  Enum<ENUM_PRODUCTION_ID>,
                REDUCTION_TYPE extends CFG_Reduction
                        <ENUM_TERMINAL_ID, TERMINAL_VALUE_TYPE, ENUM_PRODUCTION_ID, REDUCTION_TYPE>>
{

    public CFG
            <ENUM_TERMINAL_ID,
                    TERMINAL_VALUE_TYPE,
                    ENUM_PRODUCTION_ID,
                    REDUCTION_TYPE>
            cfg;

    public SeekableBuffer<CFG_Terminal<ENUM_TERMINAL_ID,
                        TERMINAL_VALUE_TYPE,
                        ENUM_PRODUCTION_ID,
                        REDUCTION_TYPE>>
            input;

    public CFG_NonDeterministicTransition
            <ENUM_TERMINAL_ID,
                    TERMINAL_VALUE_TYPE,
                    ENUM_PRODUCTION_ID,
                    REDUCTION_TYPE>
            parse_tree;

    public CFG_Reduction
            <ENUM_TERMINAL_ID,
                    TERMINAL_VALUE_TYPE,
                    ENUM_PRODUCTION_ID,
                    REDUCTION_TYPE>

    parse(SeekableBuffer<CFG_Terminal
                    <ENUM_TERMINAL_ID,
          TERMINAL_VALUE_TYPE,
          ENUM_PRODUCTION_ID,
          REDUCTION_TYPE>


                  input) {
        while (true) {
            CFG_Terminal
                    <ENUM_TERMINAL_ID,
                            TERMINAL_VALUE_TYPE,
                            ENUM_PRODUCTION_ID,
                            REDUCTION_TYPE>
                    sym = input.next();

            if (sym == terminals.get_eof()) {
                if (
        }
    }
}
