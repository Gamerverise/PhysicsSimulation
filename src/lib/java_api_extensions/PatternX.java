package java_extensions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternX {
    public Pattern pattern;

    public PatternX(String pattern) {
        this.pattern = Pattern.compile(pattern);
    }

    public Matcher match(String string) {
        return pattern.matcher(string);
    }

    public static Matcher get_matches(String pattern, String line) {
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(line);
        m.matches();
        return m;
    }

    public static Matcher get_matches(Pattern pattern, String line) {
        Matcher m = pattern.matcher(line);
        m.matches();
        return m;
    }
}
