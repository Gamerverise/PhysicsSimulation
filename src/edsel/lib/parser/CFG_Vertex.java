package edsel.lib.parser;

import edsel.lib.data_structure.graph.Vertex;

public class CFG_Vertex<EXPANSION_TYPE> extends Vertex<CFG_Vertex<EXPANSION_TYPE>> {
    EXPANSION_TYPE expansion;
}
