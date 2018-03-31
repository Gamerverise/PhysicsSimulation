package java_lang_extensions.var_args;

public class VarArgsRaw extends VarArgs<Object> {

    // Use VarArgs like this:
    //
    //    class Foo {
    //
    //        Foo(VarArgsRaw... vargs) {
    //            Integer[] local_ints = vargs[0].args<Integer>();
    //            Double[] local_doubles = vargs[1].args<Double>();
    //            String[] local_strings = vargs[2].args<String();
    //        }
    //
    //        void test() {
    //            Foo foo = new Foo(_vargs<Integer>(i_1, i_2, i_3),
    //                              _vargs<Double>(d_1, d_2, d_3)
    //                              _vargs<String>(s_1, s_2, s_3));
    //        }
    //    }

    @SafeVarargs
    protected <T> VarArgsRaw(T... args) {        // Yes, private!
        super(args);
    }

    @SuppressWarnings("unchecked")
    public <T> T[] args() {
        return (T[])args;
    }

    @SafeVarargs
    @SuppressWarnings("unchecked")
    static public <T> VarArgs<T> _vargs(T... args) {
        return (VarArgs<T>) new VarArgsRaw((Object[]) args);
    }
}
