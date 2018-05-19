package edsel.lib.io;

import edsel.lib.cfg_parser.parse_node.Token;
import lib.java_lang_extensions.parametrized_types.Instantiator;
import lib.tokens.enums.CopyType;

public abstract class TokenBuffer
        <ENUM_TERMINAL_ID extends Enum<ENUM_TERMINAL_ID>,
                TOKEN_VALUE_TYPE,
                TOKEN_BUFFER_TYPE
                        extends TokenBuffer<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE, TOKEN_BUFFER_TYPE>>
        extends
        CharBuffer<TOKEN_BUFFER_TYPE>
{
    public byte[] separator_chars;

    public TokenBuffer(String filename)
    {
        this(filename, new byte[] {' ', '\n', '\t'});
    }

    public TokenBuffer(String filename, byte[] separator_chars)
    {
        super(filename);
        this.separator_chars = separator_chars;
    }

    public TokenBuffer(TokenBuffer buf, CopyType copy_type)
    {
        super(buf, copy_type);
    }

    // =========================================================================================

    public abstract Token<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE> token_advance();

    public void eat_separators() {
        while (cursor_pos < buf.length)
            if (is_separator(buf[cursor_pos]))
                cursor_pos++;
            else
                break;
    }

    public boolean is_separator(byte chr) {
        for(byte cur : separator_chars)
            if (chr == cur)
                return true;

        return false;
    }
}
