package lib.java_lang_extensions.parametrized_types;


import lib.debug.MethodNameHack;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static lib.debug.AssertMessages.BAD_CODE_PATH;
import static lib.debug.Debug.assert_msg;

public interface
ConstructableElementX
<T_RAW_TYPE
        extends ConstructableElementX,
        T_PARAMETRIZED_TYPE
                extends T_RAW_TYPE,
        T_CONSTRUCTABLE_TYPE
                extends ConstructableElementX<T_RAW_TYPE, T_PARAMETRIZED_TYPE, T_CONSTRUCTABLE_TYPE>>
{
    static <ST extends ConstructableElementX>
    ST new_instance(Class<ST> runtime_type, Object... args) {
        ST copy;

        Class[] arg_types = new Class[args.length];

        for (int i = 0; i < args.length; i++)
            arg_types[i] = args[i].getClass();

        try {
            Constructor<ST> constructor = runtime_type.getDeclaredConstructor(arg_types);
            copy = constructor.newInstance(args);

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