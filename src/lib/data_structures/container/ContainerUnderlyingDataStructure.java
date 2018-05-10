package lib.data_structures.container;

public abstract class
ContainerUnderlyingDataStructure
        <DT, DS extends ContainerUnderlyingDataStructure<DT, DS>>
        extends
        ContainerAdapter<DT, DS, DS>
{
    public abstract void add(DT datum);
}
