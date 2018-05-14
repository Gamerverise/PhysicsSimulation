package edsel.lib.cfg_model;

import edsel.lib.cfg_parser.reductions.ReductionTerminal;
import edsel.lib.io.TokenBuffer.TokenBufferString;

public abstract
class RCFG_Terminal
        <ENUM_TERMINAL_ID extends Enum<ENUM_TERMINAL_ID>,
                REDUCTION_TYPE extends ReductionTerminal>
        extends
        CFG_Terminal<ENUM_TERMINAL_ID>
{
    public RCFG_Terminal(ENUM_TERMINAL_ID id, String pattern) {
        super(id, pattern);
    }

    public abstract REDUCTION_TYPE reduce(TokenBufferString string);
}
