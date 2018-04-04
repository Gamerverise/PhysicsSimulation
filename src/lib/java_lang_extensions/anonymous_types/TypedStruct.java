package lib.java_lang_extensions.anonymous_types;

public class TypedStruct<N extends Enum<N>, V> {
    V[] values;

    public TypedStruct(Class<N> clazz) {
        int num_fields = clazz.getClass().getEnumConstants().length;
        values = (V[]) new Object[num_fields];
    }

    public TypedStruct(StructField<N, V> field, StructField<N, V>... fields) {
        int num_fields = field.name.getClass().getEnumConstants().length;

        values = (V[]) new Object[num_fields];

        put(field.name, field.value);
        put(fields);
    }

    public <T> V get(N name) {
        return values[name.ordinal()];
    }

    public void put(N name, V value) {
        values[name.ordinal()] = value;
    }

    public void put(StructField<N, V>... fields) {
        for (StructField<N, V> f : fields)
            values[f.name.ordinal()] = f.value;
    }

    static <N extends Enum<N>, V> TypedStruct<N, V> S(Class<N> clazz) {
        return new Struct(clazz);
    }

    static <N extends Enum<N>, V> TypedStruct<N, V> S(StructField<N, V> field, StructField<N, V>... fields) {
        return new Struct(field, fields);
    }
}
