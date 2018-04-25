package lib.java_lang_extensions.parametrized_class;

import lib.debug.MethodNameHack;

import static lib.debug.AssertMessages.BAD_CODE_PATH;
import static lib.debug.Debug.assert_msg;

public class ParametrizedClass {

    @Deprecated
    @SuppressWarnings("unchecked")
    public static <U> U new_instance(Class clazz) {
        U copy;

        try {
            copy = (U) clazz.newInstance();

        } catch (IllegalAccessException | InstantiationException e) {
            MethodNameHack hack = new MethodNameHack() {};

            assert false : assert_msg(
                    hack.method_name().getClass(),
                    hack.method_name(),
                    BAD_CODE_PATH);

            return null;
        }

        return copy;
    }
}
