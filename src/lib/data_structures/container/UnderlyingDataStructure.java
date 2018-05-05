package lib.data_structures.container;

import lib.java_lang_extensions.parametrized_class.Constructable;

public interface UnderlyingDataStructure<DT> extends Constructable<UnderlyingDataStructure<DT>>
{
    void add(DT datum);
}
