package edsel.lib.parser.lalr;

import edsel.primordial.token.ExpansionToken;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.CharBuffer;

import static edsel.primordial.token.SectionToken.SectionTokenType;
import static edsel.primordial.token.SectionToken.SectionTokenType.VERSION_BLOCK;

public class LALR_Parser {

    int src_code_size;
    String src_code;

    SectionTokenType cur_section = VERSION_BLOCK;

    public LALR_Parser(String filename, int max_size) {
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

    public ExpansionToken next_token() {

    }
}
