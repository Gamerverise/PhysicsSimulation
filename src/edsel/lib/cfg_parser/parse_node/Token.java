package edsel.lib.cfg_parser.parse_node;

import edsel.lib.cfg_parser.SymbolBufferSymbol;
import edsel.lib.io.CharBuffer;
import lib.tokens.enums.CopyType;

import static lib.tokens.enums.CopyType.COPY_DEEP;

public class Token
        <ENUM_TOKEN_ID extends Enum<ENUM_TOKEN_ID>,
                TOKEN_VALUE_TYPE>
        extends
        ParseNode
        implements
        SymbolBufferSymbol
{
    public ENUM_TOKEN_ID id;
    public TOKEN_VALUE_TYPE value;

    public Token(ENUM_TOKEN_ID id,
                 TOKEN_VALUE_TYPE value,
                 CharBuffer<? extends CharBuffer>.CharBufferString src_string)
    {
        super(src_string);
        this.id = id;
        this.value = value;
    }

    public Token(
            Token<ENUM_TOKEN_ID, TOKEN_VALUE_TYPE> tok, CopyType copy_type) {
        super(copy_type == COPY_DEEP
                ? tok.src_string.new_copy(copy_type)
                : tok.src_string);

        this.id = tok.id;
        this.value = tok.value;
    }

    // =========================================================================================

    public String sprint_id() {
        return id.toString();
    }

    public StringBuilder sprint() {
        return new StringBuilder(id.toString())
                .append(": ")
                .append(src_string.get_string());
    }
}
