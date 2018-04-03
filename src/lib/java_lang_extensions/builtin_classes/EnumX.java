package lib.java_lang_extensions.builtin_classes;

import lib.java_lang_extensions.anonymous_types.StructField;

public interface EnumX<E extends Enum<E>, V> {
    StructField<E, V> _(V value);
}
