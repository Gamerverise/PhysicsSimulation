package lib.java_api_extensions;

public class MathX {

    public static double max(double... nums) {
        if (nums.length == 0)
            return Double.NaN;

        double max = nums[0];

        for (int i = 1; i < nums.length; i++)
            if (nums[i] > max)
                max = nums[i];

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