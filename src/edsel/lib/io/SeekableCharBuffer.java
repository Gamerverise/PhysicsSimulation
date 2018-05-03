package edsel.lib.io;

public class SeekableCharBuffer {
    public char[] buf;
    public int cur_pos;

    public int next() {
        if (cur_pos < buf.length)
            return buf[cur_pos++];

        return -1;
    }

    public void backup() {
        backup(1);
    }
    public void backup(int n) {
        cur_pos -= n;
    }
}
