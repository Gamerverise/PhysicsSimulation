package lib.java_lang_extensions.anonymous_types;

public class Struct<N extends Enum<N>> extends TypedStruct<N, Object> {

    public Struct(Class<N> clazz) {
        super(clazz);
    }

    public Struct(StructField<N, Object> field, StructField<N, Object>... fields) {
        super(field, fields);
    }

    public <T> T get(N name) {
        return (T) values[name.ordinal()];
    }
}
