package lib.java_lang_extensions.anonymous_types;

import lib.data_structures.CopyType;
import lib.java_lang_extensions.function_types.FunctionR2;
import lib.java_lang_extensions.overload_extensions.CopyTypeOverloadConstants.*;

import static lib.data_structures.CopyType.*;

public class Tuple<T> {
    T[] data;

    Tuple(int n) {
        data = (T[]) new Object[n];
    }

    Tuple(SHALLOW sig_extension, T... values) {
        data = values;
    }

    Tuple(DEEP sig_extension, FunctionR2<T, T, CopyType> ctor, T... values) {
        data = (T[]) new Object[values.length];
        for (int i = 0; i < values.length; i++)
            data[i] = ctor.call(values[i], DEEP);
    }

    <K extends T> K get(int i) {
        return (K) data[i];
    }

    void put(int i, T elem) {
        data[i] = elem;
    }
}
