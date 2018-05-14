package edsel.lib.io;

import edsel.lib.cfg_parser.reductions.ReductionBase;
import edsel.lib.io.TokenBuffer.TokenBufferString;

import static lib.text_io.FormattedText.spaces;

public class Token
        <ENUM_TOKEN_ID extends Enum<ENUM_TOKEN_ID>,
                TOKEN_VALUE_TYPE>
        extends
        ReductionBase
{
    public ENUM_TOKEN_ID id;
    public TOKEN_VALUE_TYPE value;

    public Token(ENUM_TOKEN_ID id, TOKEN_VALUE_TYPE value, TokenBufferString src_string) {
        super(src_string);
        this.id = id;
        this.value = value;
    }

    public String print(int tree_level) {
        return spaces(tree_level) + id.toString() + ": " + src_string.get_string();
    }
}
