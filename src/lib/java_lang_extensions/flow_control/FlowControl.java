package lib.java_lang_extensions.flow_control;

import lib.java_lang_extensions.function_types.*;

public class FlowControl {

    public static void repeat(int count, FunctionV0 f) {
        for (int i = 0; i < count; i++)
            f.call();
    }

    public static void repeat(int count, FunctionV1<Integer> f) {
        for (int i = 0; i < count; i++)
            f.call(i);
    }
}
