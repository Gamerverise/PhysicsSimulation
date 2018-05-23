package edsel.lib.cfg_parser.parsing_restriction;

import edsel.lib.cfg_parser.parse_node.Token;

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
