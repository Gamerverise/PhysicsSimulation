package lib.text_io;

public class FormattedText {
    public static String spaces(int n) {
        String spaces = "";

        for (int i = 0; i < n; i++)
            spaces += " ";

        return spaces;
    }
}
