package edsel.lib.cfg_model;

import edsel.lib.io.Token;

public abstract
class RCFG_Terminal
        <ENUM_TERMINAL_ID extends Enum<ENUM_TERMINAL_ID>,
                TOKEN_VALUE_TYPE>
        extends
        CFG_Terminal<ENUM_TERMINAL_ID>
{
    public RCFG_Terminal(ENUM_TERMINAL_ID id, String pattern) {
        super(id, pattern);
    }

    public abstract void reduce(Token<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE> tok);
}
