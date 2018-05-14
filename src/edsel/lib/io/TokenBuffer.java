package edsel.lib.io;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract
class TokenBuffer
        <ENUM_TOKEN_ID extends Enum<ENUM_TOKEN_ID>,
                TOKEN_VALUE_TYPE>
{
    public class TokenBufferString
    {
        public int src_start;
        public int src_end;

        public TokenBufferString(int src_start, int src_end) {
            this.src_start = src_start;
            this.src_end = src_end;
        }

        public String get_string()
        {
            return new String(buf, src_start, src_end);
        }
    }

    public byte[] separator_chars = {' ', '\n', '\t'};

    public Token<ENUM_TOKEN_ID, TOKEN_VALUE_TYPE> next_token = null;
    
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
    
    public Token<ENUM_TOKEN_ID, TOKEN_VALUE_TYPE> next() {
        eat_separators();

        Token<ENUM_TOKEN_ID, TOKEN_VALUE_TYPE> cur_token = next_token;

        next_token = specialized_next();

        return cur_token;
    }

    public abstract Token<ENUM_TOKEN_ID, TOKEN_VALUE_TYPE> specialized_next();

    public Token<ENUM_TOKEN_ID, TOKEN_VALUE_TYPE> peek() {
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
