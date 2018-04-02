package lib_2.java_lang_extensions.anonymous_types;

import java.util.HashMap;

public class Struct<N extends Enum, V> {
    HashMap<N, V> hash_map;

    public Struct(StructField<N, V>... fields) {
        hash_map = new HashMap<>();
        put(fields);
    }

    // FIXME
    public <K extends V> K get(N name) {
        return (K) hash_map.get(name);
    }

    public void put(N name, V value) {
        hash_map.put(name, value);
    }

    public void put(StructField<N, V>... fields) {
        for (StructField<N, V> f : fields)
            hash_map.put(f.name, f.value);
    }
}
