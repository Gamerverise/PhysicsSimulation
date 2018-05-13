package edsel.lib.io;

import edsel.lib.cfg_model.CFG_Terminal;
import lib.java_lang_extensions.tuples.Range_int;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class
CFG_TerminalBuffer<ENUM_TERMINAL_ID extends Enum<ENUM_TERMINAL_ID>>
{
    public byte[] separator_chars = {' ', '\n', '\t'};

    public ENUM_TERMINAL_ID cur_id;
    public int cur_tok_start;
    public int cur_tok_end;
    
    public byte[] buf;

    public CFG_TerminalBuffer(String filename)
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
    CFG_Terminal<ENUM_TERMINAL_ID, Range_int>
    next()
    {
        CFG_Terminal<ENUM_TERMINAL_ID, Range_int>
                range = new CFG_Terminal<>(cur_id, new Range_int(cur_tok_start, cur_tok_end));
        eat_separators();
        return range;
    }

    public
    CFG_Terminal<ENUM_TERMINAL_ID, Range_int>
    peek()
    {
        return new CFG_Terminal<>(cur_id, new Range_int(cur_tok_start, cur_tok_end));
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
    }

    public boolean is_separator(byte chr) {
        for(byte cur : separator_chars)
            if (chr == cur)
                return true;

        return false;
    }

    public String get_str(Range_int range) {
        return new String(buf, range.start, range.size());
    }
}
