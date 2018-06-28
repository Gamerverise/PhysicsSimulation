package edsel.lib.cfg_model;

import edsel.lib.io.CharBuffer;

import java.util.Objects;

public class CFG
        <ENUM_PRODUCTION_ID extends Enum<ENUM_PRODUCTION_ID>,
                ENUM_TERMINAL_ID extends Enum<ENUM_TERMINAL_ID>,
                TOKEN_VALUE_TYPE>
{
    public CFG_Production<ENUM_PRODUCTION_ID> start_production;

    public CFG_Production<ENUM_PRODUCTION_ID>[] productions;
    public CFG_Terminal<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE>[] terminals;

    public CFG(
            CFG_Production<ENUM_PRODUCTION_ID> start_production,
            CFG_Production<ENUM_PRODUCTION_ID>[] productions,
            CFG_Terminal<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE>[] terminals)
    {
        this.start_production = start_production;
        this.productions = productions;
        this.terminals = terminals;
    }


    public CFG_Production<ENUM_PRODUCTION_ID>
    get_production(CharBuffer.CharBufferString production_name) {
        for (CFG_Production<ENUM_PRODUCTION_ID> production : productions)
            if (Objects.equals(production_name.get_string(), production.name))
                return production;

        return null;
    }

    public CFG_Terminal<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE>
    get_terminal(CharBuffer.CharBufferString terminal_name) {
        String terminal_name_str = terminal_name.get_string();

        for (CFG_Terminal<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE> terminal : terminals)
            if (Objects.equals(terminal_name_str, terminal.name))
                return terminal;

        return null;
    }
}
