package edsel.lib.cfg_parser;

import edsel.lib.cfg_model.CFG_Symbol;
import edsel.lib.cfg_model.CFG_Terminal;
import edsel.lib.cfg_model.RCFG_Production;

public class TerminalEntry
        <PRODUCTION_TYPE extends RCFG_Production<ENUM_PRODUCTION_ID, REDUCTION_TYPE>,
                ENUM_PRODUCTION_ID extends Enum<ENUM_PRODUCTION_ID>,
                REDUCTION_TYPE>
        extends
        ParseStateEntry
{
    public TerminalEntry(CFG_Terminal terminal, ParseStateEntry prev_state)
    {
        super(prev_state);
        this.terminal = terminal;
    }

    public CFG_Symbol terminal;
}
