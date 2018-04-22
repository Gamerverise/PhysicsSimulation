package lib.data_structures;

import lib.debug.MethodNameHack;
import lib.tokens.enums.CopyType;

import static lib.debug.AssertMessages.BAD_CODE_PATH;
import static lib.debug.Debug.assert_msg;

public class Copyable<T extends Copyable<T>> {

    public T new_copy(CopyType copy_type) {
        T copy;

        try {
            copy = (T) getClass().newInstance();

        } catch (IllegalAccessException | InstantiationException e) {

            assert false : assert_msg(
                    this.getClass(),
                    new MethodNameHack() {}.method_name(),
                    BAD_CODE_PATH);

            return null;
        }

        return copy;
    }
}
