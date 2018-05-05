package lib.java_lang_extensions.parametrized_types;


import lib.tokens.enums.CopyType;

public interface
ConstructableBase
        <T extends CONSTRUCTABLE_TYPE,
                CONSTRUCTABLE_TYPE extends ConstructableBase<T, CONSTRUCTABLE_TYPE>>
{
    T new_instance(Object... args);

    T new_copy(CopyType copy_type);
}