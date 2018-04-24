package lib.data_structures.container;

public abstract class ContainerAdapter
        <DATA_TYPE, UNDERLYING_DATA_STRUCTURE>
        implements Iterable<DATA_TYPE>
{
    public UNDERLYING_DATA_STRUCTURE contents;

    public ContainerAdapter() {}

    public ContainerAdapter(UNDERLYING_DATA_STRUCTURE contents) {
        this.contents = contents;
    }

    @SuppressWarnings("unchecked")
    public <D> D cast() {
        return (D) contents;
    }
}
