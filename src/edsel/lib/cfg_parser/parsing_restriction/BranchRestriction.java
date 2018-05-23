package edsel.lib.cfg_parser.parsing_restriction;

import edsel.lib.cfg_model.CFG_Production;

public class BranchRestriction<ENUM_PRODUCTION_ID extends Enum<ENUM_PRODUCTION_ID>>
        extends ProductionRestriction<ENUM_PRODUCTION_ID>
{
    public int branch_num;

    public BranchRestriction(RestrictionMode mode) {
        super(mode);
    }

    public BranchRestriction(
            CFG_Production<ENUM_PRODUCTION_ID> production,
            int branch_num,
            RestrictionMode mode)
    {
        super(production, mode);
        this.branch_num = branch_num;
    }
}
