package lib.java_lang_extensions.parametrized_types;

import lib.tokens.enums.CopyType;

import static lib.tokens.enums.CopyType.COPY_SHALLOW;

public abstract class
InstantiatorBase<T extends InstantiatorBase<T>>
        implements Instantiator<T>
{
    public T new_copy() {
        return new_copy(COPY_SHALLOW);
    }

    public T new_copy(CopyType copy_type) {
        return new_instance(this, copy_type);
    }
}
