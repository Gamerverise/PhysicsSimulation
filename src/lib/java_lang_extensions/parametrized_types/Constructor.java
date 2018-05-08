package lib.java_lang_extensions.parametrized_types;

import lib.debug.MethodNameHack;
import lib.tokens.enums.CopyType;

import java.lang.reflect.InvocationTargetException;

import static lib.debug.AssertMessages.BAD_CODE_PATH;
import static lib.debug.Debug.assert_msg;

public interface Constructor<T>
{
    T new_instance(Object... args);

    T new_copy(CopyType copy_type);

    static <RAW_TYPE, PARAMETRIZED_TYPE extends RAW_TYPE>
    PARAMETRIZED_TYPE new_instance(Class<RAW_TYPE> runtime_type, Object... args) {
        PARAMETRIZED_TYPE copy;

        Class[] arg_types = new Class[args.length];

        for (int i = 0; i < args.length; i++)
            arg_types[i] = args[i].getClass();

        try {
            java.lang.reflect.Constructor<RAW_TYPE> constructor = runtime_type.getDeclaredConstructor(arg_types);
            copy = (PARAMETRIZED_TYPE) constructor.newInstance(args);

        } catch (NoSuchMethodException
                | IllegalAccessException
                | InstantiationException
                | InvocationTargetException e)
        {
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
