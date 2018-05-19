package edsel.lib.io;

import lib.java_lang_extensions.parametrized_types.Instantiator;
import lib.tokens.enums.CopyType;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class CharBuffer<CHAR_BUFFER_TYPE extends CharBuffer<CHAR_BUFFER_TYPE>>
        extends InstantiatorBase<CHAR_BUFFER_TYPE>
{
    public int cursor_pos = 0;
    public byte[] buf;

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

    public CHAR_BUFFER_TYPE self() {
        return this;
    }

    public CHAR_BUFFER_TYPE new_instance(Object... args) {
        return Instantiator.new_instance(CharBuffer.class, args);
    }

    // =========================================================================================

    public CharBufferString get_CharBufferString(int start, int end) {
        return new CharBufferString(start, end);
    }

    public String get_string(int start, int end) {
        return new String(buf, start, end);
    }

    // =========================================================================================

    public class CharBufferString
            extends
            InstantiatorBase<CharBufferString>
    {
        public int src_start;
        public int src_end;

        public CharBufferString(int src_start, int src_end) {
            this.src_start = src_start;
            this.src_end = src_end;
        }

        public CharBufferString(CharBufferString string, CopyType copy_type) {
            this.src_start = string.src_start;
            this.src_end = string.src_end;
        }

        // =========================================================================================

        public CharBufferString self() {
            return this;
        }

        public CharBufferString new_instance(Object... args) {
            return Instantiator.new_instance(CharBufferString.class, args);
        }

        // =========================================================================================

        public String get_string() {
            return new String(buf, src_start, src_end);
        }
    }
}
