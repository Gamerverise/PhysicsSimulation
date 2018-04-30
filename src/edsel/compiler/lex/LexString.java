package edsel.compiler.lex;

import java.util.LinkedList;

public class LexString {

    public LinkedList<Integer> chars = new LinkedList<>();

    public String convert() {
        char[] chr_array = new char[chars.size()];

        for (int i = 0; i < chr_array.length; i++)
            chr_array[i] = (char) (int) chars.get(i);

        return new String(chr_array);
    }
}
