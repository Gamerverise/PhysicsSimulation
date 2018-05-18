package edsel.lib.cfg_parser;

import edsel.lib.cfg_model.*;
import edsel.lib.cfg_parser.exception.AmbiguousParserInput;
import edsel.lib.cfg_parser.exception.InputNotAccepted;
import edsel.lib.cfg_parser.parse_node.Reduction;

public abstract class CFG_Parser
        <ENUM_PRODUCTION_ID extends Enum<ENUM_PRODUCTION_ID>,
                ENUM_TERMINAL_ID extends Enum<ENUM_TERMINAL_ID>,
                TOKEN_VALUE_TYPE>
{
    public CFG_Production<ENUM_PRODUCTION_ID>[]                    productions;
    public CFG_Production<ENUM_PRODUCTION_ID>                      start_production;
    public CFG_Terminal<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE>[]      terminals;

    @SafeVarargs
    public CFG_Parser(
            CFG_Production<ENUM_PRODUCTION_ID>      start_production,
            CFG_Production<ENUM_PRODUCTION_ID>...   productions)
    {
        this.start_production = start_production;
        this.productions = productions;
    }

    public abstract Reduction<ENUM_PRODUCTION_ID>
    parse_recursive(SymbolBuffer<ENUM_PRODUCTION_ID, ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE> input)
            throws AmbiguousParserInput, InputNotAccepted;

    public abstract Reduction<ENUM_PRODUCTION_ID>
    parse_recursive(
            CFG_Production<ENUM_PRODUCTION_ID> production,
            SymbolBuffer<ENUM_PRODUCTION_ID, ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE> input,
            int num_branches_explored
    )
            throws AmbiguousParserInput, InputNotAccepted;

    public CFG_Production<ENUM_PRODUCTION_ID>
    get_production(
            SymbolBuffer
                    <ENUM_PRODUCTION_ID,
                            ENUM_TERMINAL_ID,
                            TOKEN_VALUE_TYPE>.SymbolBufferString production_name)
    {
        for(CFG_Production<ENUM_PRODUCTION_ID> production : productions)
            if (production_name.get_string() == production.name)
                return production;

        return null;
    }

    public CFG_Terminal<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE>
    get_terminal(SymbolBuffer
                         <ENUM_PRODUCTION_ID,
                                 ENUM_TERMINAL_ID,
                                 TOKEN_VALUE_TYPE>.SymbolBufferString terminal_name)
    {
        for(CFG_Terminal<ENUM_TERMINAL_ID,  TOKEN_VALUE_TYPE> terminal : terminals)
            if (terminal_name.get_string() == terminal.name)
                return terminal;

        return null;
    }
}
