package lib.data_structures.container;

import lib.data_structures.Copyable;

public abstract class UnderlyingDataStructure<T extends Copyable<T>> {

    public abstract void add_item(T item);
}
