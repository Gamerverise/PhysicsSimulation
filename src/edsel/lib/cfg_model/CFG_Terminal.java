package edsel.lib.cfg_model;

import edsel.lib.cfg_parser.parse_node.Token;
import edsel.lib.io.CharBuffer;
import edsel.lib.io.TokenBuffer;

public class
CFG_Terminal
        <ENUM_TERMINAL_ID extends Enum<ENUM_TERMINAL_ID>,
                TOKEN_VALUE_TYPE,
                TOKEN_BUFFER_TYPE
                        extends TokenBuffer<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE, TOKEN_BUFFER_TYPE>>
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

    public
    Token<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE>
    reduce(CharBuffer<TOKEN_BUFFER_TYPE>.CharBufferString str)
    { return new Token<>(id, null, str); }

    public String sprint_id() {
        return id.toString();
    }

    public StringBuilder sprint() {
        return new StringBuilder(sprint_id());
    }
}
