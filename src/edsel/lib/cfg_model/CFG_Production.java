package edsel.lib.cfg_model;

import edsel.lib.cfg_parser.parse_node.ParseNode;
import edsel.lib.cfg_parser.parse_node.Reduction;
import edsel.lib.io.CharBuffer;

public class
CFG_Production<ENUM_PRODUCTION_ID extends Enum<ENUM_PRODUCTION_ID>>
        implements
        CFG_Symbol
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

    public String sprint_id() {
        return id.toString();
    }

    public StringBuilder sprint_branch(int branch_num) {
        StringBuilder result = new StringBuilder("Branch ").append(branch_num).append(":");

        for (int i = 0; i < rhs[branch_num].length; i++)
            result.append(" ").append(rhs[branch_num][i].sprint_id());

        return result;
    }

    public StringBuilder sprint() {
        StringBuilder result = new StringBuilder("Production ").append(sprint_id()).append(": ");

        int i;
        for (i = 0; i < rhs.length; i++)
            result.append(" ").append(sprint_branch(i));

        if (i == 0)
            result.append("degenerate production has no branches");

        return result;
    }
}
