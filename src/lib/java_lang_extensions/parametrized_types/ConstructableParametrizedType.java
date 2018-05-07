package lib.java_lang_extensions.parametrized_types;


import lib.tokens.enums.CopyType;

public interface
ConstructableParametrizedType
        <T_PARAMETRIZED_TYPE
                extends ConstructableParametrizedType<T_PARAMETRIZED_TYPE>>
    extends ConstructableRawType
{
    T_PARAMETRIZED_TYPE new_instance(Object... args);

    T new_copy(CopyType copy_type);
}