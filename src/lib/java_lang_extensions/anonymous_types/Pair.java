package lib.java_lang_extensions.anonymous_types;

public class Pair<T> extends PairUV<T, T> {
    public Pair(T _1, T _2) {
        super(_1, _2);
    }

    @SuppressWarnings("unchecked")
    public static <A> Pair<A> P(A _1, A _2) {
        return new Pair(_1, _2);
    }
}
