package edsel.lib.cfg_parser;

import lib.data_structures.list.link.LinkLegacy;

public class SymbolBufferState {
    LinkLegacy<SymbolBufferSymbol> symbol_cursor = null;

    public SymbolBufferState() {}

    public SymbolBufferState(SymbolBufferState state) {
        symbol_cursor = state.symbol_cursor;
    }
}
