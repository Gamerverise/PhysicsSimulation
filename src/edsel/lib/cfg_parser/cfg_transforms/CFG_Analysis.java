package edsel.lib.cfg_parser.cfg_transforms;

import edsel.lib.cfg_model.CFG_Production;
import edsel.lib.cfg_model.CFG_Symbol;
import edsel.lib.cfg_model.CFG_Terminal;
import lib.data_structures.list.LinkedListLegacy;

public class CFG_Analysis
        <ENUM_PRODUCTION_ID extends Enum<ENUM_PRODUCTION_ID>,
                ENUM_TERMINAL_ID extends Enum<ENUM_TERMINAL_ID>>
{
    public LinkedListLegacy<CFG_Production<ENUM_PRODUCTION_ID>>[] lalr_parse_table;

    // A start-eof ambiguity is a case where the start symbol can be reduced before the EOF is reached.

    public void find_start_eof_ambiguities() {}

    public void find_empty_productions() {}

    public void find_unreachable_productions() {}

    public void find_irreducible_productions() {}

    public void find_productions_with_no_terminal_symbols() {}

    public void find_asociativity_orderings() {}        // left-to-right or right-to-left

    public void find_associativity_ambiguities() {}     // Are all ambiguities associativity ambiguities?

    public void find_ambiguities() {}                   // Are all ambiguities associativity ambiguities?

    public CFG_Analysis<ENUM_PRODUCTION_ID, ENUM_TERMINAL_ID>
    build_lalr_k_parse_table(CFG_Production<ENUM_PRODUCTION_ID> start_production, int k)
    {
        return null;
    }

    public CFG_Analysis<ENUM_PRODUCTION_ID, ENUM_TERMINAL_ID>
    build_lalr_1_parse_table(CFG_Production<ENUM_PRODUCTION_ID> start_production)
    {
        for (int i = 0; i < start_production.rhs.length; i++) {

            CFG_Symbol[] branch = start_production.rhs[i];

            for (CFG_Symbol symbol : branch) {

            }
        }

        return null;
    }

    public LinkedListLegacy<CFG_Symbol>
    terminal_transition(
            CFG_Production<ENUM_PRODUCTION_ID> start_production)
    {
        for (int i = 0; i < start_production.rhs.length; i++) {

            CFG_Symbol[] branch = start_production.rhs[i];

            for (CFG_Symbol symbol : branch) {

                if (symbol instanceof CFG_Terminal)
                    return new LinkedListLegacy<>(symbol);
            }
        }

        return null;
    }
}
