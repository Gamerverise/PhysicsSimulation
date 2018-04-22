package lib.data_structures;

import lib.debug.MethodNameHack;

import static lib.debug.AssertMessages.BAD_CODE_PATH;
import static lib.debug.Debug.assert_msg;

public class Copy {

    @SuppressWarnings("unchecked")
    public static <U> U new_copy(Object o) {
        U copy;

        try {
            copy = (U) o.getClass().newInstance();

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
