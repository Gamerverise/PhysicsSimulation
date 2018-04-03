package lib.java_lang_extensions.anonymous_types;

public class StructField<N extends Enum, V> {
    N name;
    V value;

    public StructField(N name, V value) {
        this.name = name;
        this.value = value;
    }

    public static <N extends Enum, V> StructField<N, V> _(N name, V value) {
        return new StructField<>(name, value);
    }
}
