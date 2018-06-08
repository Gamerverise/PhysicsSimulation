package edsel.lib.cfg_parser.non_deterministic;

import edsel.lib.cfg_model.CFG_Symbol;
import lib.data_structures.list.link.LinkLegacy;

public class NonDetParsingStateFrame
{
    public LinkLegacy<CFG_Symbol> exploration_stack_ptr = null;

    public int num_prefixes_in_progress = 0;
    public int num_prefixes_to_retire = 0;

    public NonDetParsingStateFrame(LinkLegacy<CFG_Symbol> exploration_stack_ptr) {
        this.exploration_stack_ptr = exploration_stack_ptr;
    }

    public NonDetParsingStateFrame(NonDetParsingStateFrame info) {
        exploration_stack_ptr = info.exploration_stack_ptr;
        num_prefixes_in_progress = info.num_prefixes_in_progress;
        num_prefixes_to_retire = info.num_prefixes_to_retire;
    }
}
