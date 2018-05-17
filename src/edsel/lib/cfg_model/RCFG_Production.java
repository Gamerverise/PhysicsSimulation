package edsel.lib.cfg_model;

import edsel.lib.cfg_parser.RCFG_Parser.SymbolBuffer.SymbolBufferString;
import edsel.lib.cfg_parser.parse_node.ParseNode;
import edsel.lib.cfg_parser.parse_node.Reduction;

public class RCFG_Production
        <ENUM_PRODUCTION_ID extends Enum<ENUM_PRODUCTION_ID>>
        extends
        CFG_Production<ENUM_PRODUCTION_ID>
{
    public RCFG_Symbol_Multi symbol_multi;

    public void init(ENUM_PRODUCTION_ID id, String name, CFG_Symbol[]... rhs) {
        super.init(id, rhs);
        symbol_multi = new RCFG_Symbol_Multi(name);
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
