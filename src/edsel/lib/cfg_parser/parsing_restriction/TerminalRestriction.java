package edsel.lib.cfg_parser.parsing_restriction;

import edsel.lib.cfg_parser.parse_node.Token;
import edsel.lib.cfg_parser.parsing_restriction.ParsingRestriction;

import static lib.text_io.FormattedText.spaces;

public class TerminalRestriction
        <ENUM_TERMINAL_ID extends Enum<ENUM_TERMINAL_ID>,
                TOKEN_VALUE_TYPE>
        extends
        ParsingRestriction
{
    public Token<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE> token;

    public TerminalRestriction() {}

    public TerminalRestriction(Token<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE> token) {
        this.token = token;
    }

    public StringBuilder sprint() {
        return new StringBuilder("Terminal Restriction: ").append(token.sprint_id());
    }
}
