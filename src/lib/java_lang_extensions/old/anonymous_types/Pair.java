package lib.java_lang_extensions.old.anonymous_types;

import lib.tokens.classes.X;
import lib.java_lang_extensions.structs.XY;
import lib.tokens.classes.Y;

public class Pair {

    public static XY P(X<Double> x, Y<Double> y) {
        return new XY(x.x, y.y);
    }
}
