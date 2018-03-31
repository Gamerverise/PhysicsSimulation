package testing.real_time_model;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Pattern;

import testing.*;
import compile.*;
import gui.GUI;

public class RealTimeModelTest extends TestBase {

    public void do_test(Scanner scanner, String test_data_path)
            throws IOException
    {
        InputSpec[] input_specs = {
                new InputSpec("block_range", "Block range to print (x y n) [10 12 100]: ", 10, 12, 100),
        };

        get_test_input(scanner, test_data_path, input_specs);

        int x = (int)input_specs[0].parsed_values[0].value;
        int y = (int)input_specs[0].parsed_values[1].value;
        int n = (int)input_specs[0].parsed_values[2].value;

        RealTimeModel rtm = new RealTimeModel(GUI.map_img, GUI.robot_img);

        System.out.print(rtm.map.sprint_range(x, y, n));
    }
}

//        System.out.println("Enter your username: ");
//        String username = scanner.nextLine();
//        String default_img_path = "C:\\Users\\Gamerverise\\FRC_2018\\IdeaProjects\\FRC_2018\\.idea\\data_files\\Map\\RealTimeMap_Test.png";

//        Pattern path_pattern = Pattern.compile(".*\\n");
//        System.out.print("Path to image [" + default_img_path + "]: ");
//        String img_path = scanner.nextLine();
//
//        if (img_path.equals(""))
//            img_path = default_img_path;
