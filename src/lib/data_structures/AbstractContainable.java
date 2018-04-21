package lib.data_structures;

import lib.tokens.enums.CopyType;

public interface AbstractContainable<T extends AbstractContainable> {

    AbstractContainable<T> new_copy(CopyType copy_type);
}
