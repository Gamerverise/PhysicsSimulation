package lib.java_lang_extensions.parametrized_types;


import lib.debug.MethodNameHack;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static lib.debug.AssertMessages.BAD_CODE_PATH;
import static lib.debug.Debug.assert_msg;

public interface
ConstructableX
        <T,
                RAW_DATA_STRUCTURE_TYPE,
                PARAMETRIZED_DATA_STRUCTURE_TYPE extends RAW_DATA_STRUCTURE_TYPE, CONSTRUCTABLE_TYPE,
                CONSTRUCTABLE_TYPE
                        extends ConstructableX
                                <T, RAW_DATA_STRUCTURE_TYPE, PARAMETRIZED_DATA_STRUCTURE_TYPE, CONSTRUCTABLE_TYPE>>
{
    static <ST extends ConstructableX>
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