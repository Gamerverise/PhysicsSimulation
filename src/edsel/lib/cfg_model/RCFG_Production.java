package edsel.lib.cfg_model;

import edsel.lib.cfg_parser.parse_node.ParseNode;
import edsel.lib.cfg_parser.parse_node.ReductionParseNode;
import edsel.lib.io.ParseNodeBuffer.ParseNodeBufferString;

public class RCFG_Production
        <ENUM_PRODUCTION_ID extends Enum<ENUM_PRODUCTION_ID>>
        extends
        CFG_Production<ENUM_PRODUCTION_ID>
{
    public String name;

    public void init(ENUM_PRODUCTION_ID id, String name, CFG_Symbol[]... rhs) {
        super.init(id, rhs);
        this.name = name;
    }

    public ReductionParseNode<ENUM_PRODUCTION_ID>
    reduce(int branch_num,
           ParseNode[] sub_reductions,
           int num_branches_explored,
           ParseNodeBufferString src_string)
    {
        return new ReductionParseNode<>
                (this.id, branch_num, sub_reductions, num_branches_explored, src_string);
    }
}
