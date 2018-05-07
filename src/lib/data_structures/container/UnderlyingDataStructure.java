package lib.data_structures.container;

public interface UnderlyingDataStructure<DT> extends ConstructableParametrizedType<UnderlyingDataStructure<DT>>
{
    void add(DT datum);
}
