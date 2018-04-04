package lib.java_lang_extensions.anonymous_types;

public class TypedStruct<N extends Enum<N>, V> {
    V[] values;

    @SuppressWarnings("unchecked")
    public TypedStruct(Class<N> clazz) {
        int num_fields = clazz.getClass().getEnumConstants().length;
        values = (V[]) new Object[num_fields];
    }

    @SafeVarargs
    @SuppressWarnings("unchecked")
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


    @SuppressWarnings("unchecked")
    public void put(StructField<N, V>... fields) {
        for (StructField<N, V> f : fields)
            values[f.name.ordinal()] = f.value;
    }

    @SuppressWarnings("unchecked")
    public static <N extends Enum<N>, V> TypedStruct<N, V> TS(Class<N> clazz) {
        return new Struct(clazz);
    }

    @SuppressWarnings("unchecked")
    public static <N extends Enum<N>, V> TypedStruct<N, V> TS(StructField<N, V> field, StructField<N, V>... fields) {
        return new Struct(field, fields);
    }
}
