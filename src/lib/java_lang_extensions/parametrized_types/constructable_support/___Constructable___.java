package lib.java_lang_extensions.parametrized_types.constructable_support;

import lib.tokens.enums.CopyType;

public interface ___Constructable___
        <A extends Precondition_RawConstructable & Precondition_ParametrizedConstructable<A>,
                B extends A>
        extends Precondition_RawConstructable, Precondition_ParametrizedConstructable<A>
{
    B new_instance(Object... args);

    B new_copy(CopyType copy_type);
}
