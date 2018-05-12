package edsel.lib.cfg_parser;

import edsel.lib.cfg_model.CFG_Terminal;

public class TerminalEntry
        <ENUM_TERMINAL_ID extends Enum<ENUM_TERMINAL_ID>,
                TERMINAL_VALUE_TYPE,
                ENUM_PRODUCTION_ID extends Enum<ENUM_PRODUCTION_ID>,
                REDUCTION_TYPE>
        extends
        ParseStateEntry<ENUM_TERMINAL_ID, ENUM_PRODUCTION_ID, REDUCTION_TYPE>
{
    public CFG_Terminal<ENUM_TERMINAL_ID, TERMINAL_VALUE_TYPE>
            terminal;

    public TerminalEntry(
            CFG_Terminal<ENUM_TERMINAL_ID, TERMINAL_VALUE_TYPE>
                    terminal,
            ParseStateEntry<ENUM_TERMINAL_ID, ENUM_PRODUCTION_ID, REDUCTION_TYPE>
                    prev_state)
    {
        super(prev_state);
        this.terminal = terminal;
    }
}
