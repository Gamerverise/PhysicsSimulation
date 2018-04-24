package lib.data_structures;


import lib.tokens.enums.CopyType;

public interface Copyable<T extends Copyable<T>> {

    T new_copy(CopyType copy_type);
}
