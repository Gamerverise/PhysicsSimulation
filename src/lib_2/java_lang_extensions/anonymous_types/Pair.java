package lib_2.java_lang_extensions.anonymous_types;

public class Pair<A, B> {
    A a;
    B b;

    Pair() {}

    Pair(A a, B b) {
        this.a = a;
        this.b = b;
    }

    static <A, B> Pair<A, B> _(A a, B b) {
        return new Pair(a, b);
    }
}
