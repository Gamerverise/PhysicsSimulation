package edsel.lib.cfg_parser.parsing_restriction;

import edsel.lib.cfg_model.CFG_Terminal;

public class TerminalRestriction
        <ENUM_TERMINAL_ID extends Enum<ENUM_TERMINAL_ID>,
                TOKEN_VALUE_TYPE>
        extends
        ParsingRestriction
{
    public CFG_Terminal<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE> terminal;

    public TerminalRestriction(
            CFG_Terminal<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE> terminal,
            RestrictionMode mode)
    {
        super(mode);
        this.terminal = terminal;
    }
}
