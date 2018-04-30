package edsel.primordial.token;

public class ExpansionToken {
    ExpansionTokenType type;
    String string;

    public static class ExpansionTokenString {

        public static String IDENTIFIER                 = "[_A-Za-z0-9]+";

        public static String SEPARATOR                  = "[ \t\n]+";
        public static String ESCAPE                     = "\"";
        public static String SEPARATOR_QUOTE            = "\"";
        public static String IDENTIFIER_OPEN            = "<";
        public static String IDENTIFIER_CLOSE           = ">";
        public static String OPTION_OPEN                = "[";
        public static String OPTION_CLOSE               = "]";
        public static String DISJUNCTION_OP             = "|";
        public static String STAR_OP                    = "*";
        public static String PLUS_OP                    = "+";
        public static String GROUP_OPEN                 = "(";
        public static String GROUP_CLOSE                = ")";
        public static String PRODUCTION_OP              = "->";
        public static String FORCE_REDUCTION_OP         = "#";
    }

    public enum ExpansionTokenType {

        IDENTIFIER                 ,

        SEPARATOR                  ,
        ESCAPE                     ,
        SEPARATOR_QUOTES           ,
        IDENTIFIER_DELIMS          ,
        OPTION_DELIMS              ,
        DISJUNCTION_OP             ,
        STAR_OP                    ,
        PLUS_OP                    ,
        GROUP_OP                   ,
        PRODUCTION_OP              ,
        FORCE_REDUCTION_OP
    }

    public static ExpansionToken next_token(String buffer) {

    }
}
