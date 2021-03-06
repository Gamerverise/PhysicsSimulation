package lib.compile;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;

import lib.java_api_extensions.*;

public class InputParser {
    static final String ASSIGNMENT_RE = "^[ \\t]*([A-Za-z_]+)[ \\t]*=(.*)";
    static final String NEXT_INT_RE = "^[ \\t]*(0|[1-9][0-9]*)(.*)";
    static final String NEXT_REAL_ISH_RE = "^[ \\t]*((0|[1-9][0-9]*)(\\.[0-9]*)?)(.*)";

    // compiled regular expression (CRE)

    static final PatternX ASSIGNMENT_CRE = new PatternX(ASSIGNMENT_RE);
    static final PatternX NEXT_INT_CRE = new PatternX(NEXT_INT_RE);
    static final PatternX NEXT_REAL_ISH_CRE = new PatternX(NEXT_REAL_ISH_RE);

    public static void parse_lines(BufferedReader lines, InputSpec[] specs)
        throws IOException
    {
        HashMap<String, InputSpec> spec_table = new HashMap<>();

        for (InputSpec spec : specs)
            spec_table.put(spec.parse_name, spec);

        String line;
        for (int i = 0; (line = lines.readLine()) != null; i++) {
            Matcher m = ASSIGNMENT_CRE.match(line);
            parse_values(m.group(2), spec_table.get(m.group(1)));
        }
    }

    public static void parse_values(String line, InputSpec spec) {
        if (line.equals(""))
            return;

        for (int i = 0; i < spec.parsed_values.length; i++) {
            InputSpec.Atom atom = spec.parsed_values[i];

            if (atom.value instanceof Integer) {
                Matcher m = NEXT_INT_CRE.match(line);
                atom.value = Integer.parseInt(m.group(1));
                line = m.group(2);
            } else if (atom.value instanceof Float) {
                Matcher m = NEXT_REAL_ISH_CRE.match(line);
                atom.value = Float.parseFloat(m.group(1));
                line = m.group(4);
            } else if (atom.value instanceof Double) {
                Matcher m = NEXT_REAL_ISH_CRE.match(line);
                atom.value = Double.parseDouble(m.group(1));
                line = m.group(4);
            } else
                throw new RuntimeException("Unimplemented InputSpec mode.");
        }
    }
}
