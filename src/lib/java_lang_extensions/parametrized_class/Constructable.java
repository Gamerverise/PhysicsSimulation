package lib.java_lang_extensions.parametrized_class;


import lib.debug.MethodNameHack;
import lib.tokens.enums.CopyType;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static lib.debug.AssertMessages.BAD_CODE_PATH;
import static lib.debug.Debug.assert_msg;

public interface
Constructable
        <T extends CONSTRUCTABLE_TYPE,
                CONSTRUCTABLE_TYPE extends Constructable<T, CONSTRUCTABLE_TYPE>>
{
    T new_copy(CopyType copy_type);

    // =========================================================================================

    static <STE extends ST, ST extends Constructable<ST>>
    STE new_instance(Class<STE> runtime_type, Object... args) {
        STE copy;

        Class[] arg_types = new Class[args.length];

        for (int i = 0; i < args.length; i++)
            arg_types[i] = args[i].getClass();

        try {
            Constructor<STE> constructor = runtime_type.getDeclaredConstructor(arg_types);
            copy = constructor.newInstance(args);

        } catch (NoSuchMethodException
                | IllegalAccessException
                | InstantiationException
                | InvocationTargetException e) {
            MethodNameHack hack = new MethodNameHack() {
            };

            assert false : assert_msg(
                    hack.method_name().getClass(),
                    hack.method_name(),
                    BAD_CODE_PATH);

            return null;
        }

        return copy;
    }
}