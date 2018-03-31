package testing.reckoning;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;

import testing.*;
import compile.*;

public class WheelBasedReckoningTest extends TestBase {

    public void do_test(Scanner scanner, String test_data_path)
            throws IOException
    {
        InputSpec[] input_specs = {
                new InputSpec("rotation_precision", "Rotation precision [360]: ", 360),
                new InputSpec("wheel_diameter", "Wheel diameter [3]: ", 3d),
                new InputSpec("track", "Track (on-center between front wheels (or rear wheels)) [36]: ", 36d),
                new InputSpec("starting_coords", "Starting coordinates (x, y, direction) [0 0 0]: ", 0d, 0d, 0d),
                new InputSpec("revolution_pulses", "Revolution pulses (left, right) [10 10]: ", 10, 10)
        };

        get_test_input(scanner, test_data_path, input_specs);

        int pulses_per_revolution = (int)input_specs[0].parsed_values[0].value;
        double wheel_diameter = (double)input_specs[1].parsed_values[0].value;
        double track = (double)input_specs[2].parsed_values[0].value;
        double start_x = (double)input_specs[3].parsed_values[0].value;
        double start_y = (double)input_specs[3].parsed_values[1].value;
        double start_direction = (double)input_specs[3].parsed_values[2].value;
        int left_pulses = (int)input_specs[4].parsed_values[0].value;
        int right_pulses = (int)input_specs[4].parsed_values[1].value;

        WheelBasedReckoning wbr = new WheelBasedReckoning(
                pulses_per_revolution,
                wheel_diameter,
                track,
                start_x,
                start_y,
                start_direction
        );

        double dist_moved = wbr.update_coords(left_pulses, right_pulses);
        System.out.println("Distance moved: " + dist_moved);
        System.out.println(wbr.toString());
        System.out.println();
    }
}
