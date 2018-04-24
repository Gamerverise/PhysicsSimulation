package lib.data_structures.container;

import lib.data_structures.Copyable;

public abstract class UnderlyingDataStructure<DT, DS extends CDS, CDS extends Copyable<DS, CDS>>
    implements Copyable<DS, CDS>
{
    public abstract void add_item(DT item);
}
