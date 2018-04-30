package edsel.primordial.token;

public class SectionToken {
    SectionTokenType type;
    String string;

    public static class SectionTokenString {

        public static String VERSION_REQUIRED            = "VERSION_REQUIRED";
        public static String THIS_VERSION                = "THIS_VERSION";
        
        public static String BLOCK_SEPARATOR             = "[ \t\n]+";
    }

    public enum SectionTokenType {

        VERSION_BLOCK                   ,
        TOKEN_BLOCK                     ,
        PRODUCTION_BLOCK                ,
        CODE_BLOCK                      ,

        BLOCK_SEPARATOR
    }
}
