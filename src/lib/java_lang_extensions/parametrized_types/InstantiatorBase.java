package lib.java_lang_extensions.parametrized_types;

import lib.tokens.enums.CopyType;

import static lib.tokens.enums.CopyType.COPY_SHALLOW;

public abstract class
InstantiatorBase<INSTANTIATOR_BASE_TYPE extends InstantiatorBase>
        implements Instantiator<INSTANTIATOR_BASE_TYPE>
{
    public INSTANTIATOR_BASE_TYPE new_copy() {
        return new_copy(COPY_SHALLOW);
    }

    public INSTANTIATOR_BASE_TYPE new_copy(CopyType copy_type) {
        return new_instance(this, copy_type);
    }

    // =========================================================================================

    public INSTANTIATOR_BASE_TYPE self() {
        return this;
    }

    public INSTANTIATOR_BASE_TYPE new_instance(Object... args) {
        return Instantiator.new_instance(Instantiator.class, args);
    }
}
