package edsel.compiler.edselex_grammar;

public class EdselexProductionSet {

    EdselexSymbol[] production;

    public EdselexProductionSet(EdselexSymbol... production) {
        this.production = production;
    }

    public static EdselexProductionSet SUB_EXPR = new EdselexProductionSet(
            ESCAPE,
            OR,
            GROUP,
            OR,
            CLASS,
            OR,
            COMPLEMENT_CLASS,
            OR,
            REPETITION
    );

    public static EdselexProductionSet ESCAPE = new EdselexProductionSet(
            ESCAPE_CHAR,
            LITERAL_CHAR
    );

    public static EdselexProductionSet GROUP = new EdselexProductionSet(
            INLINE_GROUP,
            OR,
            REF_GROUP
    );

    public static EdselexProductionSet INLINE_GROUP = new EdselexProductionSet();

    public static EdselexProductionSet REF_GROUP = new EdselexProductionSet();

    public static EdselexProductionSet REF_GROUP_CLOSE = new EdselexProductionSet();

    public static EdselexProductionSet CLASS = new EdselexProductionSet();

    public static EdselexProductionSet COMPLEMENT_CLASS = new EdselexProductionSet();

    public static EdselexProductionSet REPETITION = new EdselexProductionSet();
}
