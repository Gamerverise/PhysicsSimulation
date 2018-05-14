package edsel.lib.io;

import edsel.lib.io.TokenBuffer.TokenBufferString;

public class Token
        <ENUM_TOKEN_ID extends Enum<ENUM_TOKEN_ID>,
                TOKEN_VALUE_TYPE>
{
    public ENUM_TOKEN_ID id;
    public TOKEN_VALUE_TYPE value;
    public TokenBufferString src_string;

    public Token(ENUM_TOKEN_ID id, TOKEN_VALUE_TYPE value, TokenBufferString src_string) {
        this.id = id;
        this.value = value;
        this.src_string = src_string;
    }
}
