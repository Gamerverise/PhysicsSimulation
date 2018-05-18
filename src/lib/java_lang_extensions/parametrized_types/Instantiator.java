package lib.java_lang_extensions.parametrized_types;

import java.lang.reflect.InvocationTargetException;

import lib.debug.MethodNameHack;
import lib.tokens.enums.CopyType;

import static lib.debug.AssertMessages.BAD_CODE_PATH;
import static lib.debug.Debug.assert_msg;

public class
Instantiator<INSTANCE_TYPE>
{
    public
    <PARAMETRIZED_TYPE extends RAW_TYPE, RAW_TYPE>
    PARAMETRIZED_TYPE
    new_instance() {
        return Instantiator.static_new_instance((Class<RAW_TYPE>)this.getClass());
    }

    public
    <PARAMETRIZED_TYPE extends RAW_TYPE, RAW_TYPE>
    PARAMETRIZED_TYPE
    new_instance(Class<RAW_TYPE> runtime_type, Object... args) {
        return Instantiator.static_new_instance(runtime_type, args);
    }

    // =========================================================================================

    static <RAW_TYPE, PARAMETRIZED_TYPE extends RAW_TYPE>
    PARAMETRIZED_TYPE static_new_instance(Class<RAW_TYPE> runtime_type, Object... args) {
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
