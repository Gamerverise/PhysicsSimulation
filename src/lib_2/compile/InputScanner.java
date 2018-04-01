package lib_2.compile;

import java.util.Scanner;

public class InputScanner {
    public static void scan_input(Scanner scanner, InputSpec[] input_specs) {
        for (int i = 0; i < input_specs.length; i++) {
            InputSpec input_spec = input_specs[i];
            System.out.println(input_spec.prompt);

            String line = scanner.nextLine();

            InputParser.parse_values(line, input_spec);
        }
    }
}
