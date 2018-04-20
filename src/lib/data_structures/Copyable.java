package lib.data_structures;

import lib.tokens.enums.CopyType;

public interface Copyable<T> {

    Copyable<T> copy(CopyType copy_type);
}
