package lib.data_structures;

import lib.tokens.enums.CopyType;

public interface ContainerDataStructure<T extends Containable, D> extends Iterable<T> {

    ContainerDataStructure<T, D> new_copy(CopyType copy_type);

    void add_item(Containable<T> item);
}
