package edsel.lib.io;

import edsel.lib.cfg_model.CFG_Symbol;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;

public class
SeekableCFG_TerminalBuffer
        <ENUM_TERMINAL_ID extends Enum<ENUM_TERMINAL_ID>,
                TERMINAL_VALUE_TYPE,
                ENUM_PRODUCTION_ID extends Enum<ENUM_PRODUCTION_ID>>
    extends SeekableBufferBase
{
    byte[] input;

    CFG_Symbol
            <ENUM_TERMINAL_ID, TERMINAL_VALUE_TYPE, ENUM_PRODUCTION_ID>
            eof;

    LinkedList<Integer> token_starts = new LinkedList<>();

    public SeekableCFG_TerminalBuffer(
            String filename,
            CFG_Symbol<ENUM_TERMINAL_ID, TERMINAL_VALUE_TYPE, ENUM_PRODUCTION_ID>
                    eof)
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

    public
    CFG_Symbol
            <ENUM_TERMINAL_ID,
                TERMINAL_VALUE_TYPE,
                ENUM_PRODUCTION_ID>
    next() {
        return null;
    }
}
