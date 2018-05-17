package edsel.lib.cfg_model;

import edsel.lib.cfg_parser.parse_node.Token;

public abstract
class RCFG_Terminal
        <ENUM_TERMINAL_ID extends Enum<ENUM_TERMINAL_ID>, TOKEN_VALUE_TYPE>
        extends
        CFG_Terminal<ENUM_TERMINAL_ID>
        implements
        RCFG_Symbol
{
    public String name;

    public RCFG_Terminal(ENUM_TERMINAL_ID id, String pattern, String name)
    {
        super(id, pattern);
        this.name = name;
    }

    public abstract void reduce(Token<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE> tok);
}
