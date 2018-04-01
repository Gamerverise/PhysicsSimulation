package lib_2.compile;

import lib.java_lang_extensions.var_args.VarArgsRaw;

public class InputSpec {
    public static class Atom {
        public Object value;

        public Atom(Integer value) {
            this.value = value;
        }

        public Atom(Float value) {
            this.value = value;
        }

        public Atom(Double value) {
            this.value = value;
        }
    }

    public String parse_name;
    public String prompt;
    public Atom[] parsed_values;

    /***************************************************************************/

    public InputSpec(String parse_name, String prompt, Atom... parsed_values) {
        init(parse_name, prompt, parsed_values);
    }

    public InputSpec(String parse_name, String prompt, Integer... parsed_values) {
        init(parse_name, prompt, parsed_values);
    }

    public InputSpec(String parse_name, String prompt, Float... parsed_values) {
        init(parse_name, prompt, parsed_values);
    }

    public InputSpec(String parse_name, String prompt, Double... parsed_values) {
        init(parse_name, prompt, parsed_values);
    }

    /***************************************************************************/

    public InputSpec(String parse_name, String prompt, VarArgsRaw... parsed_values) {
        for (int i = 0; i < parsed_values.length; i++) {
            VarArgsRaw values = parsed_values[i];

            if (values.args().length == 0)
                continue;

            Object first_value = values.args()[0];

            if (first_value instanceof Atom)
                init(parse_name, prompt, values.<Atom>args());

            else if (first_value instanceof Integer)
                init(parse_name, prompt, values.<Integer>args());

            else if (first_value instanceof Float)
                init(parse_name, prompt, values.<Float>args());

            else if (first_value instanceof Double)
                init(parse_name, prompt, values.<Double>args());
        }
    }

    /***************************************************************************/

    public void init(String parse_name, String prompt, Atom... parsed_values) {
        this.parsed_values = parsed_values;
    }

    public void init(String parse_name, String prompt, Integer... parsed_values) {
        init(parse_name, prompt, parsed_values.length);

        for (int i = 0; i < parsed_values.length; i++)
            this.parsed_values[i] = new Atom(parsed_values[i]);
    }

    public void init(String parse_name, String prompt, Float... parsed_values) {
        init(parse_name, prompt, parsed_values.length);

        for (int i = 0; i < parsed_values.length; i++)
            this.parsed_values[i] = new Atom(parsed_values[i]);
    }

    public void init(String parse_name, String prompt, Double... parsed_values) {
        init(parse_name, prompt, parsed_values.length);

        for (int i = 0; i < parsed_values.length; i++)
            this.parsed_values[i] = new Atom(parsed_values[i]);
    }

    /***************************************************************************/

    void init(String parse_name, String prompt, int num_vals) {
        this.parse_name = parse_name;
        this.prompt = prompt;
        this.parsed_values = new Atom[num_vals];
    }

    /***************************************************************************/

    public String sprint() {
        String out = new String(parse_name + " = ");

        int i = 0;
        for (; i < parsed_values.length - 1; i++)
            out += parsed_values[i].value.toString() + " ";

        out += parsed_values[i].value.toString();

        return out;
    }

    public static String array_sprint(InputSpec[] specs) {
        String out = new String();

        int i = 0;
        for (; i < specs.length - 1; i++)
            out += specs[i].sprint() + "\n";

        out += specs[i].sprint();

        return out;
    }
}
