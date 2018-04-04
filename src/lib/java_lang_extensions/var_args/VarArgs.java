package lib.java_lang_extensions.var_args;

public class VarArgs<T> {

    // Use VarArgs like this:
    //
    //    class Foo {
    //
    //        Foo(VarArgsRaw... vargs) {
    //            Integer[] local_ints = vargs[0].<Integer>args();
    //            Double[] local_doubles = vargs[1].<Double>args();
    //            String[] local_strings = vargs[2].<String>args();
    //        }
    //
    //        void test() {
    //            Foo foo = new Foo(<Integer>_vargs(i_1, i_2, i_3),
    //                              <Double>_vargs(d_1, d_2, d_3)
    //                              <String>_vargs(s_1, s_2, s_3));
    //        }
    //    }

    protected T[] args;                   // Yes, protected!

    @SafeVarargs
    protected VarArgs(T... args) {        // Yes, protected!
        this.args = args;
    }


    public <V> T[] args() {
        return args;
    }

    @SafeVarargs
    @SuppressWarnings("unchecked")
    static public <V> VarArgs<V> _vargs(V... args) {
        return new VarArgs(args);
    }
}
