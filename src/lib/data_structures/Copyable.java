package lib.data_structures;

import lib.tokens.enums.CopyType;

public interface Copyable<T extends Copyable<T>> {

    T new_copy(CopyType copy_type);

//    public T new_copy(CopyType copy_type) {
//        T copy;
//
//        copy = ParametrizedClass.new_instance(this.getClass());
//
//        copy.copy_in((T) this, copy_type);
//
//        return copy;
//    }
//
//    public abstract void copy_in(T src, CopyType copy_type);
}
