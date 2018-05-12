package edsel.lib.io.seekable;

public abstract class SeekableBuffer<T> extends SeekableBufferBase {
    public T eof;

    public SeekableBuffer(T eof) {
        this.eof = eof;
        this.cur_pos = 0;
    }

    public abstract T next();
}
