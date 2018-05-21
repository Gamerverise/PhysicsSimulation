package lib.java_lang_extensions.parametrized_types;

import lib.tokens.enums.CopyType;

import static lib.tokens.enums.CopyType.*;

public class
Copyable<SELF_TYPE extends Copyable<SELF_TYPE>>
    extends Instantiator<SELF_TYPE>
{
    public SELF_TYPE new_copy() {
        return new_copy(COPY_SHALLOW);
    }

    public SELF_TYPE new_copy(Object enclosing_instance) {
        return new_copy(enclosing_instance, COPY_SHALLOW);
    }

    @SuppressWarnings("unchecked")
    public SELF_TYPE new_copy(CopyType copy_type) {
        return new_instance((Class<SELF_TYPE>) getClass(), this, copy_type);
    }

    @SuppressWarnings("unchecked")
    public SELF_TYPE new_copy(Object enclosing_instance, CopyType copy_type) {
        return new_instance((Class<SELF_TYPE>) getClass(), enclosing_instance, this, copy_type);
    }
}
