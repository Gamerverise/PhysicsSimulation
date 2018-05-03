package edsel.lib.parser.cfg;

import edsel.lib.data_structure.SeekableBuffer;
import edsel.lib.data_structure.graph.Vertex;
import edsel.lib.parser.grammar.GrammarTerminalSet;

public class CFG_Parser<PRODUCTION_SET, TERMINAL_SET extends GrammarTerminalSet<TERMINAL_SET>, REDUCTION_SET> {

    public CFG_Vertex<PRODUCTION_SET> grammar;
    public PRODUCTION_SET productions;
    public TERMINAL_SET terminals;
    public SeekableBuffer<TERMINAL_SET> input;

    public CFG_ParsingVertex<PRODUCTION_SET> parse_tree;

    public Vertex<REDUCTION_SET> parse(SeekableBuffer<TERMINAL_SET> input) {
        while (true) {
            TERMINAL_SET sym = input.next();

            if (sym == terminals.get_eof()) {
                if (
        }
    }
}
