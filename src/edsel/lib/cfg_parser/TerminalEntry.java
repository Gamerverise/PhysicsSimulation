package edsel.lib.cfg_parser;

import edsel.lib.cfg_model.CFG_Terminal;

public class TerminalEntry
        <ENUM_TERMINAL_ID extends Enum<ENUM_TERMINAL_ID>,
                TERMINAL_VALUE_TYPE,
                ENUM_PRODUCTION_ID extends Enum<ENUM_PRODUCTION_ID>,
                REDUCTION_TYPE>
        implements
        ParseStateEntry
{
    public CFG_Terminal<ENUM_TERMINAL_ID, TERMINAL_VALUE_TYPE>
            terminal;

    public TerminalEntry(
            CFG_Terminal<ENUM_TERMINAL_ID, TERMINAL_VALUE_TYPE>
                    terminal)
    {
        this.terminal = terminal;
    }
}
