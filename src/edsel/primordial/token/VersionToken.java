package edsel.primordial.token;

public class VersionToken {
    VersionTokenType type;
    String string;

    public static class VersionTokenString {

        public static String VERSION_REQUIRED            = "VERSION_REQUIRED";
        public static String THIS_VERSION                = "THIS_VERSION";
        public static String VERSION_SPECIFIER           = "[^ \t\n]+";
    }

    public enum VersionTokenType {

        VERSION_REQUIRED            ,
        THIS_VERSION                ,
        VERSION_SPECIFIER
    }
}
