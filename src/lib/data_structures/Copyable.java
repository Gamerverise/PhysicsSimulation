package lib.data_structures;

import lib.tokens.enums.CopyType;

public interface Copyable<DT extends BDT, BDT extends CDT, CDT extends lib.data_structures.Copyable<DT, BDT, CDT>> {

    DT new_copy(CopyType copy_type);

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
