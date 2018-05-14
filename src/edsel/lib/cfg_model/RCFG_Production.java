package edsel.lib.cfg_model;

import edsel.lib.cfg_parser.reductions.ReductionBase;
import edsel.lib.cfg_parser.reductions.Reduction;
import edsel.lib.io.TokenBuffer.TokenBufferString;

public abstract
class RCFG_Production
        <ENUM_PRODUCTION_ID extends Enum<ENUM_PRODUCTION_ID>>
        extends
        CFG_Production<ENUM_PRODUCTION_ID>
{
    public RCFG_Production() {
        super();
    }

    public RCFG_Production(ENUM_PRODUCTION_ID id, CFG_Symbol[]... rhs) {
        init(id, rhs);
    }

    public Reduction<ENUM_PRODUCTION_ID, RCFG_Production<ENUM_PRODUCTION_ID>>
    reduce(int branch_num,
           ReductionBase[] sub_reductions,
           int num_branches_explored,
           TokenBufferString src_string)
    {
        return new Reduction<>
                (this, branch_num, sub_reductions, num_branches_explored, src_string);
    }
}
