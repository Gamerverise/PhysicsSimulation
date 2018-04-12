package lib.java_lang_extensions.anonymous_types;

public class PairUV<A, B> {
    A _1;
    B _2;

    public PairUV(A _1, B _2) {
        this._1 = _1;
        this._2 = _2;
    }

    @SuppressWarnings("unchecked")
    public static <A, B> PairUV<A, B> PUV(A _1, B _2) {
        return new PairUV(_1, _2);
    }
}
