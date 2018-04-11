package lib.java_lang_extensions.anonymous_types;

public class Struct<N extends Enum<N>, V> {
    public V[] values;

    public Struct(Class<N> clazz) {
        int num_fields = clazz.getEnumConstants().length;
        values = (V[]) new Object[num_fields];
    }

    public Struct(StructField<N, V> field, StructField<N, V>... fields) {
        this((Class<N>) field.name.getClass());

        put(field.name, field.value);
        put(fields);
    }

    public V get(N name) {
        return values[name.ordinal()];
    }

    public void put(N name, V value) {
        values[name.ordinal()] = value;
    }

    public void put(StructField<N, V>... fields) {
        for (StructField<N, V> f : fields)
            values[f.name.ordinal()] = f.value;
    }

    public static <N extends Enum<N>, V> Struct<N, V> S(Class<N> clazz) {
        return new Struct<>(clazz);
    }

    public static <N extends Enum<N>, V> Struct<N, V> S(StructField<N, V> field, StructField<N, V>... fields) {
        return new Struct<>(field, fields);
    }
}
