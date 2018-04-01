package lib_2.java_lang_extensions.unnamed_types;

import java.util.HashMap;

public class Struct<T> {
    HashMap<StructField, T> hash_map;

    Struct(Object... entries) {
        hash_map = new HashMap<>();

        for (int i = 0; i < entries.length; i += 2)
            hash_map.put((StructField) entries[i], (T) entries[i+1]);
    }

    <K extends T> K get(StructField field) {
        return (K) hash_map.get(field);
    }

    void put(StructField field, T elem) {
        hash_map.put(field, elem);
    }
}
