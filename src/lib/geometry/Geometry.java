package lib.geometry;

import static lib.java_api_extensions.MathX.atan;

public class Geometry {

    public static double line_angle_degrees(double x1, double y1, double x2, double y2) {
        double dx = x2 - x1;
        double dy = y2 - y1;

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
