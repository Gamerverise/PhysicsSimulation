package edsel.lib.parser.cfg;

import java.util.List;

public class CFG_ParsingVertex<PRODUCTION_SET> {
    CFG_Vertex<PRODUCTION_SET> production_lhs;
    List<CFG_Vertex<PRODUCTION_SET>> candidate_rh_sides;
}
