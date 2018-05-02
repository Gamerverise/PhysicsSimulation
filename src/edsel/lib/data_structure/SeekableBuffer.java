package edsel.lib.data_structure;

public class SeekableBuffer<T> {
    public T[] buf;
    public T eof;
    public int cur_pos;

    public T next() {
        if (cur_pos < buf.length)
            return buf[cur_pos++];

        return eof;
    }

    public void backup() {
        backup(1);
    }
    public void backup(int n) {
        cur_pos -= n;
    }
}
