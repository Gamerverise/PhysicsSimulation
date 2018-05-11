package edsel.lib.cfg_model;

public abstract
class RCFG_Production
        <ENUM_PRODUCTION_ID extends Enum<ENUM_PRODUCTION_ID>,
                REDUCTION_TYPE>
        extends
        CFG_Production<ENUM_PRODUCTION_ID>
{
    public RCFG_Production(ENUM_PRODUCTION_ID id, CFG_Symbol[]... rhs) {
        super(id, rhs);
    }

    public abstract REDUCTION_TYPE reduce(REDUCTION_TYPE... sub_reductions);
}
