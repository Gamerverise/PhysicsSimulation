package edsel.lib.parser;

import edsel.lib.data_structure.SeekableBuffer;
import edsel.lib.data_structure.graph.Vertex;

public class CFG_Parser<EXPANSIONS, TERMINALS extends GrammarSymbol, REDUCTIONS> {

    public CFG_Vertex<EXPANSIONS> grammar;
    public EXPANSIONS expansions;
    public TERMINALS terminals;
    public SeekableBuffer<TERMINALS> input;

    public Vertex<REDUCTIONS> parse(SeekableBuffer<TERMINALS> input) {
        while (true) {
            TERMINALS sym = input.next();

            if (sym == )
        }
    }
}
