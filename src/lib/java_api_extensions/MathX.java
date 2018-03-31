package java_extensions;

public class MathX {

    static public double max(double... nums) {
        if (nums.length == 0)
            return Double.NaN;

        double max = nums[0];

        for (int i = 1; i < nums.length; i++)
            if (nums[i] > max)
                max = nums[i];

        return max;
    }
}