package edsel.lib.io;

import lib.java_lang_extensions.tuples.Range_int;

public class Token
        <ENUM_TOKEN_ID extends Enum<ENUM_TOKEN_ID>,
                TOKEN_VALUE_TYPE>
{
    public ENUM_TOKEN_ID id;
    public TOKEN_VALUE_TYPE value;
    public Range_int src_ref;

    public Token(ENUM_TOKEN_ID id, TOKEN_VALUE_TYPE value, int src_start, int src_end) {
        this.id = id;
        this.value = value;
        this.src_ref = new Range_int(src_start, src_end);
    }
}
