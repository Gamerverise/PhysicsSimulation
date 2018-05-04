package edsel.lib.io;

public class SeekableBufferBase {
    public int cur_pos = 0;

    public void backup() {
        backup(1);
    }

    public void backup(int n) {
        cur_pos -= n;

        if (cur_pos < 0)
            cur_pos = 0;
    }
}
