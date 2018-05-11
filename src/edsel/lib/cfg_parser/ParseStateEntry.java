package edsel.lib.cfg_parser;

public class ParseStateEntry
{
    public ParseStateEntry(ParseStateEntry prev_state)
    {
        this.prev_state = prev_state;
    }

    public ParseStateEntry prev_state;
}
