package edsel.lib.io;

import edsel.lib.cfg_parser.parse_node.ParseTreeNode;
import lib.java_lang_extensions.parametrized_types.Instantiator;
import lib.java_lang_extensions.parametrized_types.InstantiatorBase;
import lib.tokens.enums.CopyType;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class
ParseNodeBuffer extends InstantiatorBase<ParseNodeBuffer>
{
    public class ParseNodeBufferString
            extends
            InstantiatorBase<ParseNodeBufferString>
    {
        public int src_start;
        public int src_end;

        public ParseNodeBufferString(int src_start, int src_end) {
            this.src_start = src_start;
            this.src_end = src_end;
        }

        public ParseNodeBufferString(ParseNodeBufferString string, CopyType copy_type) {
            this.src_start = string.src_start;
            this.src_end = string.src_end;
        }

        // =========================================================================================

        public ParseNodeBufferString
        self()
        {
            return this;
        }

        public ParseNodeBufferString new_instance(Object... args) {
            return Instantiator.new_instance(ParseNodeBufferString.class, args);
        }

        // =========================================================================================

        public String get_string()
        {
            return new String(buf, src_start, src_end);
        }
    }

    public byte[] separator_chars = {' ', '\n', '\t'};

    public ParseTreeNode next_parse_node = null;
    
    public int cursor_pos = 0;

    public byte[] buf;

    public ParseNodeBuffer(String filename)
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

    public ParseNodeBuffer(ParseNodeBuffer tok_buf, CopyType copy_type)
    {
        if (copy_type == CopyType.COPY_DEEP) {
            separator_chars = new byte[tok_buf.separator_chars.length];
            System.arraycopy(tok_buf.separator_chars, 0, separator_chars, 0, tok_buf.separator_chars.length);
        } else
            separator_chars = tok_buf.separator_chars;

        next_parse_node = tok_buf.next_parse_node.new_copy(copy_type);

        cursor_pos = tok_buf.cursor_pos;
        buf = tok_buf.buf;
    }

    public ParseTreeNode advance() {
        eat_separators();

        ParseTreeNode cur_parse_node = next_parse_node;

        next_parse_node = specialized_advance();

        return cur_parse_node;
    }

    public abstract ParseTreeNode specialized_advance();

    public ParseTreeNode peek() {
        return next_parse_node;
    }

    public boolean not_empty() {
        return next_parse_node != null;
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

    public ParseNodeBufferString get_buffer_string(int start, int end)
    {
        return new ParseNodeBufferString(start, end);
    }

    public String get_string(int start, int end) {
        return new String(buf, start, end);
    }
}
