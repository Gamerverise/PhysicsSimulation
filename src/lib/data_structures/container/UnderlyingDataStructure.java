package lib.data_structures.container;

import lib.java_lang_extensions.parametrized_types.ConstructableParametrizedType;

public interface UnderlyingDataStructure<DT> extends ConstructableParametrizedType<UnderlyingDataStructure<DT>>
{
    void add(DT datum);
}
