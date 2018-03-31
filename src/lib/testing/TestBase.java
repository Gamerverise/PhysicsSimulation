package testing;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;

import java_extensions.*;

import compile.*;

public abstract class TestBase {

    public abstract void do_test(Scanner scanner, String test_data_path)
            throws IOException;

    public void get_test_input(Scanner scanner, String test_data_path, InputSpec[] input_specs)
            throws IOException
    {
        if (test_data_path.equals(""))
            InputScanner.scan_input(scanner, input_specs);
        else {
            BufferedReader lines = FileX.get_file_lines(test_data_path);
            InputParser.parse_lines(lines, input_specs);
            lines.close();

            System.out.println();
            System.out.println(InputSpec.array_sprint(input_specs));
            System.out.println();
        }
    }
}
