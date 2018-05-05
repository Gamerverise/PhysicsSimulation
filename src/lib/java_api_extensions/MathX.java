package lib.java_api_extensions;

import lib.java_lang_extensions.function_types.FunctionR1;
import lib.java_lang_extensions.function_types.Identity;
import lib.java_lang_extensions.parametrized_types.AtomicArrayIterator_double;

import java.util.Arrays;
import java.util.Iterator;

import static java.lang.Math.atan;

public class MathX {

    public static double max(double... nums) {
        return max(Identity::IDENTITY, new AtomicArrayIterator_double(nums));
    }

    @SafeVarargs
    public static <T> double max(FunctionR1<Double, T> obj_to_double_map, T... objs) {
        return max(obj_to_double_map, Arrays.asList(objs).iterator());
    }

    public static <T> double max(FunctionR1<Double, T> obj_to_double_map, Iterator<T> objs)
            throws ArithmeticException
    {

        double max;

        if (objs.hasNext())
            max = obj_to_double_map.call(objs.next());
        else
            throw new ArithmeticException();

        while (objs.hasNext()) {
            double num = obj_to_double_map.call(objs.next());

            if (num > max)
                max = num;
        }

        return max;
    }

    public static double degrees_to_radians(double degrees) {
        return degrees / 180 * Math.PI;
    }

    public static double sin(double degrees) {
        return Math.sin(degrees_to_radians(degrees));
    }

    public static double cos(double degrees) {
        return Math.cos(degrees_to_radians(degrees));
    }

    public static double tan(double degrees) {
        return Math.tan(degrees_to_radians(degrees));
    }

    public static double asin(double degrees) {
        return Math.asin(degrees_to_radians(degrees));
    }

    public static double acos(double degrees) {
        return Math.acos(degrees_to_radians(degrees));
    }

    public static double atan_vector(double dx, double dy) {
        if (dx == 0) {

            if (dy == 0)
                throw new ArithmeticException();

            if (dy > 0)
                return 90;
            else
                return -90;

        } else if (dy == 0) {

            if (dx > 0)
                return 0;
            else
                return 180;
        } else

            return atan(dy/dx);
    }
}