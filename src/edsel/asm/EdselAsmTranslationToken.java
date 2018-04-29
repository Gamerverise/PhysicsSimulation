package edsel.asm;

public class EdselAsmTranslationToken {
    ExpansionTokenType type;
    String string;

    public static class ExpansionTokenString {

        public static String ESCAPE                 = "\\";
        public static String EXPANSION_REF_QUOTE    = "\"";
        public static String SEPARATOR_QUOTE        = "\"";
        public static String EXPANSION_INDEX_OP     = ":";
        public static String EOS                    = ";";
    }

    public enum ExpansionTokenType {

        ESCAPE                  ,
        EXPANSION_REF_QUOTE     ,
        SEPARATOR_QUOTE         ,
        EXPANSION_INDEX_OP      ,
        EOS
    }
}
