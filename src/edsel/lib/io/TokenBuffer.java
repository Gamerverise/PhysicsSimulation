package edsel.lib.io;

import lib.java_lang_extensions.tuples.Range_int;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract
class TokenBuffer
        <ENUM_TERMINAL_ID extends Enum<ENUM_TERMINAL_ID>,
                TOKEN_VALUE_TYPE>
{
    public byte[] separator_chars = {' ', '\n', '\t'};

    public Token<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE> next_token;
    
    public int cur_tok_start;
    public int cur_tok_end;
    
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

        cur_tok_start = 0;
        eat_separators();
    }
    
    public
    Token<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE>
    next()
    {
        eat_separators();
        return next_token;
    }

    public
    Token<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE>
    peek()
    {
        return next_token;
    }

    public boolean not_empty() {
        return cur_tok_start < buf.length;
    }

    public void eat_separators() {
        while (cur_tok_start < buf.length)
            if (is_separator(buf[cur_tok_start]))
                cur_tok_start++;
            else
                break;

        cur_tok_end = cur_tok_start;
        
        while (cur_tok_end < buf.length)
            if (!is_separator(buf[cur_tok_end]))
                cur_tok_end++;
            else
                break;
            
        update_next();
    }
    
    public boolean is_separator(byte chr) {
        for(byte cur : separator_chars)
            if (chr == cur)
                return true;

        return false;
    }

    public String get_substr(Range_int range) {
        return get_substr(range.start, range.end);
    }

    public String get_substr(int start, int end) {
        return new String(buf, start, end);
    }
    
    public abstract void update_next();
}
