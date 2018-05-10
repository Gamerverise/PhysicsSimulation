package lib.data_structures.container;

import lib.java_lang_extensions.parametrized_types.Constructor;

public abstract class
ContainerAdapter
        <DATA_TYPE,
                UNDERLYING_DATA_STRUCTURE,
                ADAPTER extends ContainerAdapter
                        <DATA_TYPE, UNDERLYING_DATA_STRUCTURE, ADAPTER>>
        implements
        Constructor<ADAPTER>,
        Iterable<DATA_TYPE>
{
    public UNDERLYING_DATA_STRUCTURE contents;

    @SuppressWarnings("unchecked")
    public <D> D cast() {
        return (D) contents;
    }
}
