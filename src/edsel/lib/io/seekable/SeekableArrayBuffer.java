package edsel.lib.io.seekable;

public class SeekableArrayBuffer<T> extends SeekableBuffer<T> {
    public T[] buf;

    public SeekableArrayBuffer(T eof) {
        super(eof);
    }

    public T next() {
        if (cur_pos < buf.length)
            return buf[cur_pos++];

        return eof;
    }
}
