package lib.java_lang_extensions.parametrized_types;

import lib.debug.MethodNameHack;
import lib.java_lang_extensions.parametrized_types.constructable_support.ConstructableEncapsulation_2;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static lib.debug.AssertMessages.BAD_CODE_PATH;
import static lib.debug.Debug.assert_msg;

public interface Constructable          // aka ConstructableEncapsulation_3
        <T,
                DS extends ConstructableEncapsulation_2<T, DS, Constructable<T, DS>>>
        extends
        ConstructableEncapsulation_2<T, DS, Constructable<T, DS>>
{
    static <T, DS extends Constructable<T, DS>>
    DS new_instance(Class<DS> runtime_type, Object... args) {
        DS copy;

        Class[] arg_types = new Class[args.length];

        for (int i = 0; i < args.length; i++)
            arg_types[i] = args[i].getClass();

        try {
            Constructor<DS> constructor = runtime_type.getDeclaredConstructor(arg_types);
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
