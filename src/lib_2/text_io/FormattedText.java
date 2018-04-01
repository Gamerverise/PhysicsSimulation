package lib_2.text_io;

public class FormattedText {
    public static String spaces(int n) {
        String spaces = new String();

        for (int i = 0; i < n; i++)
            spaces += " ";

        return spaces;
    }
}
