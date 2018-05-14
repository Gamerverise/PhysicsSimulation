package edsel.lib.cfg_model;

import edsel.lib.cfg_parser.reductions.ReductionProduction;

public abstract
class RCFG_Production
        <ENUM_PRODUCTION_ID extends Enum<ENUM_PRODUCTION_ID>,
                REDUCTION_TYPE extends
                        ReductionProduction
                                <RCFG_Production<ENUM_PRODUCTION_ID, REDUCTION_TYPE>>>
        extends
        CFG_Production<ENUM_PRODUCTION_ID>
{
    public RCFG_Production() {
        super();
    }

    public RCFG_Production(ENUM_PRODUCTION_ID id, CFG_Symbol[]... rhs) {
        init(id, rhs);
    }

    public abstract REDUCTION_TYPE reduce(CFG_Symbol[] branch, REDUCTION_TYPE[] sub_reductions);
}
