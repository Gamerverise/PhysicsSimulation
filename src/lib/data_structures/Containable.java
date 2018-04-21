package lib.data_structures;

import lib.tokens.enums.CopyType;

public interface Containable<T extends Containable> {

    Containable<T> new_copy(CopyType copy_type);
}
