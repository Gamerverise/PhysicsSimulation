package edsel.lib.io;

import edsel.lib.cfg_parser.parse_node.Token;
import lib.tokens.enums.CopyType;

public abstract class TokenBuffer
        <ENUM_TERMINAL_ID extends Enum<ENUM_TERMINAL_ID>,
                TOKEN_VALUE_TYPE,
                TOKEN_BUFFER_TYPE
                        extends TokenBuffer<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE, TOKEN_BUFFER_TYPE>>
        extends
        CharBuffer<TOKEN_BUFFER_TYPE>
{
    public static byte[] DEFAULT_SEPARATOR_CHARS = new byte[] {' ', '\n', '\t'};

    public byte[] separator_chars;

    public TokenBuffer(String filename)
    {
        this(filename, DEFAULT_SEPARATOR_CHARS);
    }

    public TokenBuffer(String filename, byte[] separator_chars)
    {
        super(filename);
        this.separator_chars = separator_chars;
    }

    public TokenBuffer(TokenBuffer buf, CopyType copy_type)
    {
        super(buf, copy_type);

        if (copy_type == CopyType.COPY_DEEP) {
            this.separator_chars = new byte[this.buf.length];
            System.arraycopy(buf.separator_chars, 0, this.separator_chars, 0, this.separator_chars.length);
        } else
            this.separator_chars = buf.separator_chars;
    }

    // =========================================================================================

    public abstract Token<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE> next_token();

    // =========================================================================================

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
