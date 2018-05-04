package edsel.lib.io;

public class SeekableCharArrayBuffer extends SeekableBufferBase {
    public char[] buf;

    public SeekableCharArrayBuffer(char[] buf) {
        this.buf = buf;
    }

    public int next() {
        if (cur_pos < buf.length)
            return buf[cur_pos++];

        return -1;
    }
}
