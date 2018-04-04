package lib.java_lang_extensions.anonymous_types;

public class Struct<N extends Enum<N>> extends TypedStruct<N, Object> {

    public Struct(Class<N> clazz) {
        super(clazz);
    }

    @SafeVarargs
    public Struct(StructField<N, Object> field, StructField<N, Object>... fields) {
        super(field, fields);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(N name) {
        return (T) values[name.ordinal()];
    }

    @SuppressWarnings("unchecked")
    public static <N extends Enum<N>, V> Struct<N> S(Class<N> clazz) {
        return new Struct(clazz);
    }

    @SuppressWarnings("unchecked")
    public static <N extends Enum<N>, V> Struct<N> S(StructField<N, V> field, StructField<N, V>... fields) {
        return new Struct(field, fields);
    }
}
