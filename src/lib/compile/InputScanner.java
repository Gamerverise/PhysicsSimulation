package lib.compile;

import java.util.Scanner;

public class InputScanner {
    public static void scan_input(Scanner scanner, InputSpec[] input_specs) {
        for (InputSpec spec : input_specs) {
            System.out.println(spec.prompt);
            String line = scanner.nextLine();
            InputParser.parse_values(line, spec);
        }
    }
}
