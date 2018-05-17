package edsel.lib.cfg_model;

import edsel.lib.cfg_parser.RCFG_Parser.SymbolBuffer.SymbolBufferString;
import edsel.lib.cfg_parser.parse_node.ParseNode;
import edsel.lib.cfg_parser.parse_node.Reduction;

public class RCFG_Production
        <ENUM_PRODUCTION_ID extends Enum<ENUM_PRODUCTION_ID>>
        extends
        CFG_Production<ENUM_PRODUCTION_ID, RCFG_Symbol>
        implements
        RCFG_Symbol
{
    public String name;

    public void init(ENUM_PRODUCTION_ID id, String name, RCFG_Production<ENUM_PRODUCTION_ID>[]... rhs) {
        super.init(id, rhs);
        this.name = name;
    }

    public Reduction<ENUM_PRODUCTION_ID>
    reduce(int branch_num,
           ParseNode[] sub_reductions,
           int num_branches_explored,
           SymbolBufferString src_string)
    {
        return new Reduction<>
                (this.id, branch_num, sub_reductions, num_branches_explored, src_string);
    }
}
