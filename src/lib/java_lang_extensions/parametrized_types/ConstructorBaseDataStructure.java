package lib.java_lang_extensions.parametrized_types;

import lib.tokens.enums.CopyType;

public abstract
class ConstructorBaseDataStructure<T extends Constructor<T>,
                    CONSTRUCTOR_BASE_TYPE extends ConstructorBaseDataStructure<T, CONSTRUCTOR_BASE_TYPE>>
        implements ConstructorDataStructure<T, CONSTRUCTOR_BASE_TYPE>
{
    public CONSTRUCTOR_BASE_TYPE new_copy() {
        return new_instance();
    }

    public CONSTRUCTOR_BASE_TYPE new_copy(CopyType copy_type) {
        return new_instance(copy_type);
    }
}
