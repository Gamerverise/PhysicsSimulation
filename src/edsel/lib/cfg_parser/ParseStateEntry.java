package edsel.lib.cfg_parser;

public class ParseStateEntry
        <ENUM_TERMINAL_ID extends Enum<ENUM_TERMINAL_ID>,
                ENUM_PRODUCTION_ID extends Enum<ENUM_PRODUCTION_ID>,
                REDUCTION_TYPE>
{
    public ParseStateEntry<ENUM_TERMINAL_ID,
                            ENUM_PRODUCTION_ID,
                            REDUCTION_TYPE>
            prev_state;

    public ParseStateEntry(
            ParseStateEntry<ENUM_TERMINAL_ID,
                                                    ENUM_PRODUCTION_ID,
                                                    REDUCTION_TYPE>
                    prev_state)
    {
        this.prev_state = prev_state;
    }
}
