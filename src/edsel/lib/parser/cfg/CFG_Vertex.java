package edsel.lib.parser.cfg;

import edsel.lib.data_structure.graph.Vertex;
import edsel.lib.parser.grammar.GrammarTerminalSet;

import java.util.List;

public class CFG_Vertex
        <PRODUCTION_SET, TERMINAL_SET extends GrammarTerminalSet<TERMINAL_SET>>
        extends Vertex<CFG_Vertex<PRODUCTION_SET, TERMINAL_SET>>
{
    PRODUCTION_SET production_lhs;
    List<CFG_Vertex<PRODUCTION_SET, TERMINAL_SET>> production_rhs;
}
