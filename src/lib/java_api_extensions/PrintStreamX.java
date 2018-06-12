package lib.java_api_extensions;

import java.io.OutputStream;
import java.io.PrintStream;

public class PrintStreamX extends PrintStream {

    public PrintStreamX(OutputStream out)
    {
        super(out);
    }

    public void print(StringBuilder str) {
        print(str.toString());
    }

    public void println(StringBuilder str) {
        print(new StringBuilder(str).append("\n"));
    }

    public static PrintStreamX outx = new PrintStreamX(System.out);
}
