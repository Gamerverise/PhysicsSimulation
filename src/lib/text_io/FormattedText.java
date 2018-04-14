package lib.text_io;

public class FormattedText {

    public static String spaces(int n) {
        String spaces = "";

        for (int i = 0; i < n; i++)
            spaces += " ";

        return spaces;
    }

    public static String concatenate(String separator, String... strs) {

        if (strs.length == 0)
            return "";

        String result = strs[0];

        for (int i = 1; i < strs.length; i++)
            result += separator + strs[i];

        return result;
    }
}
