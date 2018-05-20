package lib.java_lang_extensions.parametrized_types;

import lib.tokens.enums.CopyType;

import static lib.tokens.enums.CopyType.*;

public class
Copyable<SELF_TYPE extends Copyable<SELF_TYPE>>
    extends Instantiator<SELF_TYPE>
{
    @SuppressWarnings("unchecked")
    public SELF_TYPE new_copy() {
        return new_instance((Class<SELF_TYPE>) getClass(), COPY_SHALLOW);
    }

    @SuppressWarnings("unchecked")
    public SELF_TYPE new_copy(CopyType copy_type) {
        return new_instance((Class<SELF_TYPE>) getClass(), copy_type);
    }
}
