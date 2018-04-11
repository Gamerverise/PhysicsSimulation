package lib.java_lang_extensions.function_types;

import javafx.scene.transform.NonInvertibleTransformException;

public interface FunctionR1_NonInvertibleExc<R, P1> {
    R call (P1 p1) throws NonInvertibleTransformException;
}
