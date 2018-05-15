package edsel.lib.io;

import lib.java_lang_extensions.parametrized_types.Instantiator;
import lib.java_lang_extensions.parametrized_types.InstantiatorBase;
import lib.tokens.enums.CopyType;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class
TokenBuffer
        <ENUM_TOKEN_ID extends Enum<ENUM_TOKEN_ID>,
                TOKEN_VALUE_TYPE,
                TOKEN_TYPE extends Token<ENUM_TOKEN_ID, TOKEN_VALUE_TYPE, TOKEN_TYPE>>
        extends
        InstantiatorBase<TokenBuffer<ENUM_TOKEN_ID, TOKEN_VALUE_TYPE, TOKEN_TYPE>>
{
    public class TokenBufferString
            extends
            InstantiatorBase<TokenBufferString>
    {
        public int src_start;
        public int src_end;

        public TokenBufferString(int src_start, int src_end) {
            this.src_start = src_start;
            this.src_end = src_end;
        }

        public TokenBufferString(TokenBufferString string, CopyType copy_type) {
            this.src_start = string.src_start;
            this.src_end = string.src_end;
        }

        // =========================================================================================

        public
        TokenBuffer<ENUM_TOKEN_ID, TOKEN_VALUE_TYPE, TOKEN_TYPE>.TokenBufferString
        self()
        {
            return this;
        }

        public TokenBufferString new_instance(Object... args) {
            return Instantiator.new_instance(TokenBufferString.class, args);
        }

        // =========================================================================================

        public String get_string()
        {
            return new String(buf, src_start, src_end);
        }
    }

    public byte[] separator_chars = {' ', '\n', '\t'};

    public Token<ENUM_TOKEN_ID, TOKEN_VALUE_TYPE, TOKEN_TYPE> next_token = null;
    
    public int cursor_pos = 0;

    public byte[] buf;

    public TokenBuffer(String filename)
    {
        try {
            Path path = FileSystems.getDefault().getPath(filename);
            long input_size = Files.size(path);

            if (input_size > Integer.MAX_VALUE)
                throw new RuntimeException();

            buf = Files.readAllBytes(path);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public TokenBuffer(
            TokenBuffer<ENUM_TOKEN_ID, TOKEN_VALUE_TYPE, TOKEN_TYPE>
                    tok_buf,
            CopyType
                    copy_type)
    {
        if (copy_type == CopyType.COPY_DEEP) {
            separator_chars = new byte[tok_buf.separator_chars.length];
            System.arraycopy(tok_buf.separator_chars, 0, separator_chars, 0, tok_buf.separator_chars.length);
        } else
            separator_chars = tok_buf.separator_chars;

        next_token = tok_buf.next_token.new_copy(copy_type);

        cursor_pos = tok_buf.cursor_pos;
        buf = tok_buf.buf;
    }

    public Token<ENUM_TOKEN_ID, TOKEN_VALUE_TYPE, TOKEN_TYPE> next() {
        eat_separators();

        Token<ENUM_TOKEN_ID, TOKEN_VALUE_TYPE, TOKEN_TYPE> cur_token = next_token;

        next_token = specialized_next();

        return cur_token;
    }

    public abstract Token<ENUM_TOKEN_ID, TOKEN_VALUE_TYPE, TOKEN_TYPE> specialized_next();

    public Token<ENUM_TOKEN_ID, TOKEN_VALUE_TYPE, TOKEN_TYPE> peek() {
        return next_token;
    }

    public boolean not_empty() {
        return next_token != null;
    }

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

    public TokenBufferString get_buffer_string(int start, int end)
    {
        return new TokenBufferString(start, end);
    }

    public String get_string(int start, int end) {
        return new String(buf, start, end);
    }
}
