package lib.java_lang_extensions.anonymous_types;

public class Struct<N extends Enum<N>, V> {

    V[] values;

    public Struct(Class<N> clazz) {
        int num_fields = clazz.getClass().getEnumConstants().length;
        values = (V[]) new Object[num_fields];
    }

    public Struct(StructField<N, V> field, StructField<N, V>... fields) {
        int num_fields = field.name.getClass().getEnumConstants().length;

        values = (V[]) new Object[num_fields];

        put(field.name, field.value);
        put(fields);
    }

    // FIXME
    public <K extends V> K get(N name) {
        return (K) values[name.ordinal()];
    }

    public void put(N name, V value) {
        values[name.ordinal()] = value;
    }

    public void put(StructField<N, V>... fields) {
        for (StructField<N, V> f : fields)
            values[f.name.ordinal()] = f.value;
    }

    static <N extends Enum<N>, V> Struct<N, V> _(Class<N> clazz) {
        return new Struct(clazz);
    }

    static <N extends Enum<N>, V> Struct<N, V> _(StructField<N, V> field, StructField<N, V>... fields) {
        return new Struct(field, fields);
    }
}
