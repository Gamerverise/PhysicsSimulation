package edsel.lib.io;

import lib.java_lang_extensions.parametrized_types.Copyable;
import lib.tokens.enums.CopyType;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class CharBuffer<CHAR_BUFFER_TYPE extends CharBuffer<CHAR_BUFFER_TYPE>>
        extends Copyable<CHAR_BUFFER_TYPE>
{
    public int cursor_pos = 0;
    public byte[] buf = null;

    public CharBuffer() {}

    public CharBuffer(String filename)
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

    public CharBuffer(CharBuffer char_buf, CopyType copy_type)
    {
        cursor_pos = char_buf.cursor_pos;

        if (copy_type == CopyType.COPY_DEEP) {
            buf = new byte[buf.length];
            System.arraycopy(char_buf.buf, 0, buf, 0, buf.length);
        } else
            buf = char_buf.buf;
    }

    // =========================================================================================

    public <STRING_TYPE> STRING_TYPE get_string(Class<STRING_TYPE> type, int start, int end) {
        if (type == CharBufferString.class)
            return (STRING_TYPE) get_CharBufferString(start, end);
        else if (type == String.class)
            return (STRING_TYPE)get_String(start, end);

        return null;
    }

    public CharBufferString get_CharBufferString(int start, int end) {
        return new CharBufferString(start, end);
    }

    public String get_String(int start, int end) {
        return new String(buf, start, end);
    }

    // =========================================================================================

    public class CharBufferString extends Copyable<CharBufferString>
    {
        public int src_start = -1;
        public int src_end = -1;

        public CharBufferString() {}

        public CharBufferString(int src_start, int src_end) {
            this.src_start = src_start;
            this.src_end = src_end;
        }

        public CharBufferString(CharBufferString string, CopyType copy_type) {
            this.src_start = string.src_start;
            this.src_end = string.src_end;
        }

        // =========================================================================================

        public String get_string() {
            return new String(buf, src_start, src_end);
        }
    }
}
