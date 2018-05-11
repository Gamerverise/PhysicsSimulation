package edsel.lib.cfg_parser.transitions;

import edsel.lib.cfg_model.RCFG_Production;
import edsel.lib.cfg_model.RCFG_Symbol;
import edsel.lib.cfg_model.RCFG_Terminal;

public class AbstractShiftTransition
        <TERMINAL_TYPE
                extends RCFG_Terminal
                        <TERMINAL_TYPE,
                                PRODUCTION_TYPE,
                                SYMBOL_TYPE,
                                ENUM_TERMINAL_ID,
                                TERMINAL_VALUE_TYPE,
                                ENUM_PRODUCTION_ID,
                                REDUCTION_TYPE>,
                PRODUCTION_TYPE
                        extends RCFG_Production<TERMINAL_TYPE,
                                PRODUCTION_TYPE,
                                SYMBOL_TYPE,
                                ENUM_TERMINAL_ID,
                                TERMINAL_VALUE_TYPE,
                                ENUM_PRODUCTION_ID,
                                REDUCTION_TYPE>,
                SYMBOL_TYPE
                        extends RCFG_Symbol<TERMINAL_TYPE,
                                PRODUCTION_TYPE,
                                SYMBOL_TYPE,
                                ENUM_TERMINAL_ID,
                                TERMINAL_VALUE_TYPE,
                                ENUM_PRODUCTION_ID,
                                REDUCTION_TYPE>,
                ENUM_TERMINAL_ID extends Enum<ENUM_TERMINAL_ID>,
                TERMINAL_VALUE_TYPE,
                ENUM_PRODUCTION_ID extends Enum<ENUM_PRODUCTION_ID>,
                REDUCTION_TYPE,
                STATE_STACK_ENTRY_TYPE
                        extends AbstractStateStackEntry
                                <TERMINAL_TYPE,
                                        PRODUCTION_TYPE,
                                        SYMBOL_TYPE,
                                        ENUM_TERMINAL_ID,
                                        TERMINAL_VALUE_TYPE,
                                        ENUM_PRODUCTION_ID,
                                        REDUCTION_TYPE,
                                        STATE_STACK_ENTRY_TYPE>>
        implements
        AbstractStateTransition
{
    public TERMINAL_TYPE terminal;

    public STATE_STACK_ENTRY_TYPE new_state;

    public AbstractShiftTransition(TERMINAL_TYPE terminal, STATE_STACK_ENTRY_TYPE new_state) {
        this.terminal = terminal;
        this.new_state = new_state;
    }
}
