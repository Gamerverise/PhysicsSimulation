package edsel.lib.cfg_parser.parsing_restriction.old;

import edsel.lib.cfg_parser.parse_node.Token;
import edsel.lib.cfg_parser.parsing_restriction.ParsingRestriction;

public class TerminalRestriction
        <ENUM_TERMINAL_ID extends Enum<ENUM_TERMINAL_ID>,
                TOKEN_VALUE_TYPE>
        implements
        ParsingRestriction
{
    public Token<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE> token;

    public TerminalRestriction() {}

    public TerminalRestriction(Token<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE> token) {
        this.token = token;
    }
}
