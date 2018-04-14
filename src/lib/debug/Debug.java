package lib.debug;

import lib.java_lang_extensions.function_types.*;

import static lib.text_io.FormattedText.concatenate;

public class Debug {

    public static String assert_msg(Class clazz, String method_name, String... msgs) {

        return clazz.getName() + "." + method_name + ": Failed assertion: " + concatenate(": ", msgs);
    }

    public static <A1, A2> void print(boolean debug_flag,
                                      FunctionR2<String, A1, A2> msg_function,
                                      A1 arg_1,
                                      A2 arg_2)
    {
        if (!debug_flag)
            return;

        System.err.print(msg_function.call(arg_1, arg_2));
    }
}
