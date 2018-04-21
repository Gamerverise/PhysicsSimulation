package lib.data_structures;

import lib.data_structures.adapter.AdapterCopyable;
import lib.tokens.enums.CopyType;

public class Copyable<T extends Copyable<T>>
        extends AdapterCopyable<T>
{

    Copyable<T> new_copy(CopyType copy_type) {
        return (Copyable<T>) super.new_copy(copy_type);
    }
}
