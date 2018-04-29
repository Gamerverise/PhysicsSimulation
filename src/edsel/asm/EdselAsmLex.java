package edsel.asm;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.CharBuffer;

public class EdselAsmLex {

    int src_code_size;
    String src_code;

    public EdselAsmLex(String filename, int max_size) {
        try {
            CharBuffer char_buf = CharBuffer.allocate(max_size);
            src_code_size = new FileReader(filename).read(char_buf);
            src_code = char_buf.toString();
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public EdselAsmExpansionToken next_expansion_token() {

    }

}
