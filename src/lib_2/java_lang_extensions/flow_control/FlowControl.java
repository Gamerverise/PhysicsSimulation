package lib_2.java_lang_extensions.flow_control;

import lib_2.java_lang_extensions.function_types.*;

public class FlowControl {

    public static void repeat(int count, Sig_void_void p) {
        for (int i = 0; i < count; i++)
            p.call();
    }

    public static void repeat(int count, Sig_void_int r) {
        for (int i = 0; i < count; i++)
            r.call(i);
    }
}
