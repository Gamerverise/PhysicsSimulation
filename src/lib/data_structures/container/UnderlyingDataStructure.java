package lib.data_structures.container;

import lib.data_structures.Copyable;

public interface UnderlyingDataStructure<DT> extends Copyable<UnderlyingDataStructure<DT>>
{
    void add(DT datum);
}
