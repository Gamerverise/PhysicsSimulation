package edsel.lib.cfg_parser.reductions;

import edsel.lib.io.TokenBuffer;
import lib.tokens.enums.CopyType;

public abstract class ReductionBase
        <ENUM_TOKEN_ID extends Enum<ENUM_TOKEN_ID>,
                TOKEN_VALUE_TYPE>
{
    public
    TokenBuffer
            <ENUM_TOKEN_ID,
                    TOKEN_VALUE_TYPE>.TokenBufferString
            src_string;

    public ReductionBase(
            TokenBuffer<ENUM_TOKEN_ID, TOKEN_VALUE_TYPE>.TokenBufferString
                    src_string)
    {
        this.src_string = src_string;
    }

    public ReductionBase(
            ReductionBase<ENUM_TOKEN_ID, TOKEN_VALUE_TYPE>
                    reduction_base,
            CopyType
                    copy_type)
    {
        if (copy_type == CopyType.COPY_DEEP)
            this.src_string = reduction_base.src_string.new_copy(copy_type);
        else
            this.src_string = reduction_base.src_string;
    }

    public abstract String print(int tree_level);
}
