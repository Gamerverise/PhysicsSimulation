package edsel.lib.cfg_parser.reductions;

import edsel.lib.io.Token;
import edsel.lib.io.TokenBuffer.TokenBufferString;

import static lib.text_io.FormattedText.spaces;

public class ReductionTerminal<TOKEN_TYPE extends Token>
    extends Reduction
{
    public TOKEN_TYPE token;

    public ReductionTerminal(TOKEN_TYPE token, TokenBufferString src_string) {
        super(src_string);
        this.token = token;
    }

    public String print(int tree_level) {
        return spaces(tree_level) + token.id.toString() + ": " + src_string.get_string();
    }
}
