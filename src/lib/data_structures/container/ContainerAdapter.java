package lib.data_structures.container;

import lib.java_lang_extensions.parametrized_types.Copyable;

public abstract class
ContainerAdapter
        <DATA_TYPE,
                UNDERLYING_DATA_STRUCTURE,
                ADAPTER extends ContainerAdapter
                        <DATA_TYPE, UNDERLYING_DATA_STRUCTURE, ADAPTER>>
        extends
        Copyable<ADAPTER>
        implements
        Iterable<DATA_TYPE>
{
    public UNDERLYING_DATA_STRUCTURE contents;

    @SuppressWarnings("unchecked")
    public <D> D cast() {
        return (D) contents;
    }
}
