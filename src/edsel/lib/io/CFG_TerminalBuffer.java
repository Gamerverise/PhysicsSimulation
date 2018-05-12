package edsel.lib.io;

import edsel.lib.cfg_model.CFG_Terminal;
import edsel.lib.io.lookahead.LookaheadBuffer;
import lib.java_lang_extensions.tuples.Range_int;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class
CFG_TerminalBuffer<TERMINAL_TYPE extends CFG_Terminal<? extends Enum, Range_int>>
{
    public TERMINAL_TYPE eof;

    public byte[] separator_chars = {' ', '\n', '\t'};

    public int cur_tok_start;
    public int cur_tok_end;
    
    public byte[] buf;

    public CFG_TerminalBuffer(String filename, TERMINAL_TYPE eof)
    {
        this.eof = eof;

        try {
            Path path = FileSystems.getDefault().getPath(filename);
            long input_size = Files.size(path);

            if (input_size > Integer.MAX_VALUE)
                throw new RuntimeException();

            buf = Files.readAllBytes(path);
        } catch (IOException e) {
            throw new RuntimeException();
        }

        this.eof = eof;

        cur_tok_start = 0;
        eat_separators();
    }
    
    public Range_int next() {
        Range_int range = new Range_int<>(cur_tok_start, cur_tok_end);
        eat_separators();
        return range;
    }

    public Range_int peek() {
        return new Range_int<>(cur_tok_start, cur_tok_end);
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
}
