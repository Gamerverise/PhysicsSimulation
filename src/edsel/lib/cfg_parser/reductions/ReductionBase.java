package edsel.lib.cfg_parser.reductions;

import edsel.lib.io.TokenBuffer.TokenBufferString;

public abstract
class ReductionBase
{
    public TokenBufferString src_string;

    public ReductionBase(TokenBufferString src_string) {
        this.src_string = src_string;
    }

    public abstract String print(int tree_level);
}
