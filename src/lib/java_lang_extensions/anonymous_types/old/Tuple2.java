package lib.java_lang_extensions.anonymous_types;

import lib.debug.MethodNameHack;

import static lib.debug.Debug.assert_msg;
import static lib.debug.AssertMessages.BAD_CODE_PATH;

public class Tuple2<N extends Enum<N>, A, B> {
    A _1;
    B _2;

    Tuple2(N name_1, A _1,
           N name_2, B _2)
    {
        put(name_1, _1);
        put(name_2, _2);
    }

    <T> T get(N name) {
        switch (name.ordinal()) {
            case 0:
                return (T) _1;
            case 1:
                return (T) _2;
        }

        assert false : assert_msg(
                this.getClass(),
                new MethodNameHack(){}.method_name(),
                BAD_CODE_PATH);

        return (T) _1;
    }

    <T> void put(N name, T elem) {
        switch (name.ordinal()) {
            case 0:
                _1 = (A) elem;
            case 1:
                _2 = (B) elem;
        }

        assert false : assert_msg(
                this.getClass(),
                new MethodNameHack() {
                }.method_name(),
                BAD_CODE_PATH);
    }

    static <N extends Enum<N>, A, B> Tuple2<N, A, B> _(N name_1, A _1, N name_2, B _2) {
        return new Tuple2(name_1, _1, name_2, _2);
    }
}
