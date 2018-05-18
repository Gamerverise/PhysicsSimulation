package edsel.lib.cfg_parser.parsing_restriction;

import edsel.lib.cfg_parser.SymbolBuffer.SymbolBufferString;

public class BranchRestriction extends ProductionRestriction
{
    public int branch_num;

    public BranchRestriction(
            SymbolBufferString production_name,
            int branch_num,
            RestrictionMode mode)
    {
        super(production_name, mode);
        this.branch_num = branch_num;
    }
}
