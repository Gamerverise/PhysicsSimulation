package lib.java_lang_extensions.anonymous_types;

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

enum Eee {A, B;

    public static int getsize(Integer a) {
    return 3;
    }
}

class Struct2<N extends Enum, V> {

    V[] values;

    public Struct2() {
        N n = ctor();
        int num_fields = n.getClass().getEnumConstants().length;
        values = (V[]) new Object[num_fields];
    }

    public Struct2(StructField<N, V>... fields) {
        int num_fields = N.class.getEnumConstants().length;

        values = (V[]) new Object[num_fields];

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
}
