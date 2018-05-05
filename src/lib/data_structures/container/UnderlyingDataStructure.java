package lib.data_structures.container;

import lib.java_lang_extensions.parametrized_types.ConstructableBase;

public interface UnderlyingDataStructure<DT> extends ConstructableBase<UnderlyingDataStructure<DT>>
{
    void add(DT datum);
}
