package lib.java_lang_extensions.parametrized_types;

import lib.tokens.enums.CopyType;

import static lib.tokens.enums.CopyType.*;

public class
Copyable<SELF_TYPE extends Copyable<SELF_TYPE>>
    extends Instantiator<SELF_TYPE>
{
    public SELF_TYPE new_copy() {
        return new_instance(Copyable.class, COPY_SHALLOW);
    }

    public SELF_TYPE new_copy(CopyType copy_type) {
        return new_instance(Copyable.class, copy_type);
    }
}
