package edsel.lib.cfg_parser.non_deterministic;

import edsel.lib.cfg_model.CFG_Production;
import edsel.lib.cfg_parser.SymbolBufferSymbol;
import edsel.lib.cfg_parser.parse_node.Reduction;
import lib.data_structures.list.link.LinkLegacy;

public class NonDetParsingStateFrame<ENUM_PRODUCTION_ID extends Enum<ENUM_PRODUCTION_ID>>
{
    public LinkLegacy<SymbolBufferSymbol> symbol_cursor = null;

    public CFG_Production<ENUM_PRODUCTION_ID> cur_production = null;
    int cur_branch = -1;

    public Reduction<ENUM_PRODUCTION_ID> reduction = null;

    public int num_prefixes_in_progress = 0;
    public int num_prefixes_to_retire = 0;

    public int exploration_depth = 0;

    public NonDetParsingStateFrame() {}

    public NonDetParsingStateFrame(NonDetParsingStateFrame<ENUM_PRODUCTION_ID> frame) {
        symbol_cursor = frame.symbol_cursor;

        cur_production = null;
        cur_branch = -1;

        reduction = null;

        num_prefixes_in_progress = frame.num_prefixes_in_progress;
        num_prefixes_to_retire = frame.num_prefixes_to_retire;

        exploration_depth = frame.exploration_depth;
    }
}
