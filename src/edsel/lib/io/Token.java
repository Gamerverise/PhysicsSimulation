package edsel.lib.io;

import edsel.lib.cfg_parser.reductions.ReductionBase;
import edsel.lib.io.TokenBuffer.TokenBufferString;
import lib.java_lang_extensions.parametrized_types.Instantiator;
import lib.tokens.enums.CopyType;

import static lib.text_io.FormattedText.spaces;
import static lib.tokens.enums.CopyType.COPY_DEEP;
import static lib.tokens.enums.CopyType.COPY_SHALLOW;

public class Token
        <ENUM_TOKEN_ID extends Enum<ENUM_TOKEN_ID>,
                TOKEN_VALUE_TYPE,
                TOKEN_TYPE extends Token<ENUM_TOKEN_ID, TOKEN_VALUE_TYPE, TOKEN_TYPE>>
        extends
        ReductionBase<ENUM_TOKEN_ID, TOKEN_VALUE_TYPE, TOKEN_TYPE>
        implements
        Instantiator<Token<ENUM_TOKEN_ID, TOKEN_VALUE_TYPE, TOKEN_TYPE>>
{
    public ENUM_TOKEN_ID id;
    public TOKEN_VALUE_TYPE value;

    public Token(ENUM_TOKEN_ID id, TOKEN_VALUE_TYPE value, TokenBufferString src_string) {
        super(src_string);
        this.id = id;
        this.value = value;
    }

    public Token(
            Token<ENUM_TOKEN_ID, TOKEN_VALUE_TYPE, TOKEN_TYPE> tok, CopyType copy_type) {
        super(
                copy_type == COPY_DEEP ?
                        tok.src_string.new_copy(copy_type) :
                        tok.src_string);

        this.id = tok.id;
        this.value = tok.value;
    }

    // =========================================================================================

    public Token<ENUM_TOKEN_ID, TOKEN_VALUE_TYPE, TOKEN_TYPE> self() {
        return this;
    }

    public Token<ENUM_TOKEN_ID, TOKEN_VALUE_TYPE, TOKEN_TYPE> new_instance(Object... args) {
            return Instantiator.new_instance(Token.class, args);
    }

    public Token<ENUM_TOKEN_ID, TOKEN_VALUE_TYPE, TOKEN_TYPE> new_copy() {
        return new_copy(COPY_SHALLOW);
    }

    public Token<ENUM_TOKEN_ID, TOKEN_VALUE_TYPE, TOKEN_TYPE> new_copy(CopyType copy_type) {
        return new_instance(this, copy_type);
    }

    // =========================================================================================

    public String print(int tree_level) {
        return spaces(tree_level) + id.toString() + ": " + src_string.get_string();
    }
}
