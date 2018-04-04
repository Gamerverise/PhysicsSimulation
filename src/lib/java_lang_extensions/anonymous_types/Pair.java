package lib.java_lang_extensions.anonymous_types;

public class Pair<A, B> {
    A _1;
    B _2;

    Pair(A _1, B _2) {
        this._1 = _1;
        this._2 = _2;
    }

    static <A, B> Pair<A, B> P(A _1, B _2) {
        return new Pair(_1, _2);
    }
}
