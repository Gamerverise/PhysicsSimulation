package lib.text_io;

public class FormattedText {

    public static String[] pre_computed_spaces = {
            "",
            " ",
            "  ",
            "   ",
            "    ",
            "     ",
            "      ",
            "       ",
            "        ",
            "         ",
            "          ",
            "           ",
            "            ",
    };

    public static String spaces(int n) {

        if (n < pre_computed_spaces.length)
            return pre_computed_spaces[n];

        StringBuilder tmp_spaces = new StringBuilder(pre_computed_spaces[pre_computed_spaces.length - 1]);

        for (int i = pre_computed_spaces.length; i < n; i++)
            tmp_spaces.append(" ");

        return tmp_spaces.toString();
    }

    public static String sprint_text_list(String separator, String... strs) {

        if (strs.length == 0)
            return "";

        StringBuilder result = new StringBuilder(strs[0]);

        for (int i = 1; i < strs.length; i++) {
            result.append(separator);
            result.append(strs[i]);
        }

        return result.toString();
    }

    public static String concatenate(String... strs) {
        StringBuilder result = new StringBuilder(strs[0]);

        for (int i = 1; i < strs.length; i++)
            result.append(strs[i]);

        return result.toString();
    }

    public static String indent(int n, String string) {
        return concatenate(spaces(n), string);
    }
}
