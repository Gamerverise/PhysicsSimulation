package lib.java_lang_extensions.old.anonymous_types;

public class PairOld<T> extends PairUV<T, T> {
    public PairOld(T _1, T _2) {
        super(_1, _2);
    }

    @SuppressWarnings("unchecked")
    public static <A> PairOld<A> P(A _1, A _2) {
        return new PairOld(_1, _2);
    }
}
