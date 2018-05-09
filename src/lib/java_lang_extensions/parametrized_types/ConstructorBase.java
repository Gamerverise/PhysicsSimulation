package lib.java_lang_extensions.parametrized_types;

import lib.tokens.enums.CopyType;

public abstract
class ConstructorBase<T extends ConstructorBase<T>>
        implements Constructor<T>
{
    public T new_copy() {
        return new_instance();
    }

    public T new_copy(CopyType copy_type) {
        return new_instance(copy_type);
    }
}
