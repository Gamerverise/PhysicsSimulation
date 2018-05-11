package edsel.lib.io;

import edsel.lib.cfg_model.CFG_Terminal;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;

public class
SeekableCFG_TerminalBuffer
        <TERMINAL_TYPE extends CFG_Terminal<ENUM_TERMINAL_ID, TERMINAL_VALUE_TYPE>,
                ENUM_TERMINAL_ID extends Enum<ENUM_TERMINAL_ID>,
                TERMINAL_VALUE_TYPE>
    extends SeekableBufferBase
{
    byte[] input;

    TERMINAL_TYPE eof;

    public SeekableCFG_TerminalBuffer(
            String filename,
            TERMINAL_TYPE eof)
    {
        try {
            Path path = FileSystems.getDefault().getPath(filename);
            long input_size = Files.size(path);

            if (input_size > Integer.MAX_VALUE)
                throw new RuntimeException();

            input = Files.readAllBytes(path);
        } catch (IOException e) {
            throw new RuntimeException();
        }

        this.eof = eof;
    }

    public TERMINAL_TYPE next() {
        return null;
    }
}
