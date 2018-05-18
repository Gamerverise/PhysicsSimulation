package edsel.lib.cfg_model;

import edsel.lib.cfg_parser.parse_node.Token;

public abstract class
CFG_Terminal
        <ENUM_TERMINAL_ID extends Enum<ENUM_TERMINAL_ID>,
                TOKEN_VALUE_TYPE>
        implements
        CFG_Symbol
{
    public ENUM_TERMINAL_ID id;
    public String pattern;
    public String name;

    public CFG_Terminal(ENUM_TERMINAL_ID id, String pattern, String name) {
        this.id = id;
        this.pattern = pattern;
        this.name = name;
    }

    public abstract void reduce(Token<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE> tok);
}
