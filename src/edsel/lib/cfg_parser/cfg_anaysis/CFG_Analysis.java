package edsel.lib.cfg_parser.cfg_anaysis;

import edsel.lib.cfg_model.CFG_Production;
import edsel.lib.cfg_model.CFG_Symbol;
import edsel.lib.cfg_model.CFG_Terminal;
import lib.data_structures.list.LinkedListLegacy;

public class CFG_Analysis
        <ENUM_PRODUCTION_ID extends Enum<ENUM_PRODUCTION_ID>,
                ENUM_TERMINAL_ID extends Enum<ENUM_TERMINAL_ID>>
{
    public LinkedListLegacy<CFG_Production<ENUM_PRODUCTION_ID>>[] lalr_parse_table;

    public CFG_Analysis<ENUM_PRODUCTION_ID, ENUM_TERMINAL_ID>
    build_lalr_1_parse_table(
            CFG_Production<ENUM_PRODUCTION_ID> start_production)
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
