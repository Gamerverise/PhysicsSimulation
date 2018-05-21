package edsel.lib.cfg_model;

import edsel.lib.cfg_parser.parse_node.ParseNode;
import edsel.lib.cfg_parser.parse_node.Reduction;
import edsel.lib.io.CharBuffer;

public class
CFG_Production<ENUM_PRODUCTION_ID extends Enum<ENUM_PRODUCTION_ID>>
        implements CFG_Symbol
{
    public ENUM_PRODUCTION_ID id;
    public CFG_Symbol[][] rhs;
    public String name;

    public CFG_Production() {}

    public void init(ENUM_PRODUCTION_ID id, String name, CFG_Symbol[]... rhs) {
        this.id = id;
        this.rhs = rhs;
        this.name = name;
    }

    public Reduction<ENUM_PRODUCTION_ID>
    reduce(int branch_num,
           ParseNode[]                                          sub_reductions,
           int                                                  num_branches_explored,
           CharBuffer<? extends CharBuffer>.CharBufferString    src_string)
    {
        return new Reduction<>
                (this.id, branch_num, sub_reductions, num_branches_explored, src_string);
    }
}
