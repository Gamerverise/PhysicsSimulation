package edsel.lib.cfg_parser.non_deterministic;

import edsel.lib.io.SeekableBuffer;
import edsel.lib.data_structure.graph.Vertex;

public class CFG_NonDetParser
        <ENUM_TERMINAL_ID extends Enum<ENUM_TERMINAL_ID>,
                TERMINAL_VALUE_TYPE,
                ENUM_PRODUCTION_ID,
                REDUCTION_TYPE>
{

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
