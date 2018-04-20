package lib.java_api_extensions;

import lib.java_lang_extensions.function_types.FunctionR1;

import java.util.Arrays;
import java.util.Iterator;

public class MathX {

    public static class UndefinedMaxExc extends Exception {}

    public static double max(double... nums)
            throws UndefinedMaxExc
    {
        return max(
                (Object d) -> (Double) d,
                Arrays.asList(nums).iterator()
        );
    }

    @SafeVarargs
    public static <T> double max(FunctionR1<Double, T> obj_to_double_map, T... objs)
            throws UndefinedMaxExc
    {
        return max(obj_to_double_map, Arrays.asList(objs).iterator());
    }

    public static <T> double max(FunctionR1<Double, T> obj_to_double_map, Iterator<T> objs)
            throws UndefinedMaxExc
    {

        double max;

        if (objs.hasNext())
            max = obj_to_double_map.call(objs.next());
        else
            throw new UndefinedMaxExc();

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

    public static double atan(double degrees) {
        return Math.atan(degrees_to_radians(degrees));
    }
}