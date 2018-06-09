package edsel.lib.cfg_parser.non_deterministic;

import edsel.lib.cfg_model.CFG_Symbol;
import edsel.lib.cfg_parser.SymbolBufferSymbol;
import lib.data_structures.list.link.LinkLegacy;

public class NonDetParsingStateFrame
{
    public LinkLegacy<SymbolBufferSymbol> symbol_cursor = null;
    public CFG_Symbol last_symbol_explored = null;

    public int num_prefixes_in_progress = 0;
    public int num_prefixes_to_retire = 0;

    public int exploration_depth = 0;

    public NonDetParsingStateFrame(NonDetParsingStateFrame frame) {
        symbol_cursor = frame.symbol_cursor;
        last_symbol_explored = frame.last_symbol_explored;

        num_prefixes_in_progress = frame.num_prefixes_in_progress;
        num_prefixes_to_retire = frame.num_prefixes_to_retire;

        exploration_depth = frame.exploration_depth;
    }
}
