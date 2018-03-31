package java_lang_extensions.flow_control;

import java.util.function.IntConsumer;

import java_lang_extensions.function_types.*;

public class FlowControl {

    public static void repeat(int count, Procedure p) {
        for (int i = 0; i < count; i++)
            p.call();
    }

    public static void repeat(int count, IntConsumer r) {
        for (int i = 0; i < count; i++)
            r.accept(i);
    }
}
