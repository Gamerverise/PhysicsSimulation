package lib.data_structures.adapter;

import lib.debug.MethodNameHack;
import lib.tokens.enums.CopyType;

import static lib.debug.AssertMessages.BAD_CODE_PATH;
import static lib.debug.Debug.assert_msg;

public class AdapterCopyable<T> {

    AdapterCopyable<T> new_copy(CopyType copy_type) {
        AdapterCopyable<T> copy;

        try {
            copy = getClass().newInstance();

        } catch (IllegalAccessException | InstantiationException e) {

            assert false : assert_msg(
                    this.getClass(),
                    new MethodNameHack() {}.method_name(),
                    BAD_CODE_PATH);

            return null;
        }

        return copy;
    }

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
