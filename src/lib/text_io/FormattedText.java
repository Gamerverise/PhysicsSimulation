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

    public static StringBuilder spaces(int n) {

        if (n < pre_computed_spaces.length)
            return new StringBuilder(pre_computed_spaces[n]);

        StringBuilder tmp_spaces = new StringBuilder(pre_computed_spaces[pre_computed_spaces.length - 1]);

        for (int i = pre_computed_spaces.length; i < n; i++)
            tmp_spaces.append(" ");

        return tmp_spaces;
    }

    public static StringBuilder sprint_text_list(String separator, String... strs) {

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < strs.length; i++)
            result.append(separator).append(strs[i]);

        return result;
    }

    public static StringBuilder sprint_text_list(String separator, StringBuilder... strs) {

        if (strs.length == 0)
            return new StringBuilder();

        for (int i = 1; i < strs.length; i++)
            strs[0].append(separator).append(strs[i]);

        return strs[0];
    }

    public static StringBuilder sprint_text_list_cow(String separator, StringBuilder... strs) {

        if (strs.length == 0)
            return new StringBuilder();

        StringBuilder result = new StringBuilder(strs[0]);

        for (int i = 1; i < strs.length; i++)
            result.append(separator).append(strs[i]);

        return result;
    }

    public static StringBuilder cat(StringBuilder... strs) {
        return concatenate(strs);
    }

    public static StringBuilder concatenate(StringBuilder... strs) {

        if (strs.length == 0)
            return new StringBuilder();

        for (int i = 1; i < strs.length; i++)
            strs[0].append(strs[i]);

        return strs[0];
    }

    public static StringBuilder cat_cow(StringBuilder... strs) {
        return concatenate_cow(strs);
    }

    public static StringBuilder concatenate_cow(StringBuilder... strs) {

        if (strs.length == 0)
            return new StringBuilder();

        StringBuilder result = new StringBuilder(strs[0]);

        for (int i = 1; i < strs.length; i++)
            result.append(strs[i]);

        return result;
    }

//    public static StringBuilder indent(int n, StringBuilder string) {
//        return cat(spaces(n), string);
//    }
//
//    public static StringBuilder indent_cow(int n, StringBuilder string) {
//        return indent(0, string);
//    }
}
