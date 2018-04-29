package edsel.asm;

public class EdselAsmParseToken {
    ParseTokenType type;
    String string;

    public static class ParseTokenString {

        public static String PARSE_KEYWORD             = "parse";
        public static String PARSE_OPEN                = "{";
        public static String PARSE_CLOSE               = "}";
    }

    public enum ParseTokenType {

        PARSE_KEYWORD           ,
        PARSE_OPEN              ,
        PARSE_CLOSE
    }
}
