package lib.compile;

import lib.java_lang_extensions.var_var_args.SubVarArgs;

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

    public InputSpec(String parse_name, String prompt, Atom[] atoms, int[] ints, float[] floats, double[] doubles) {

        // FIXME: HIGH PRIORITY: What will happen if the array value to the right of the : is null?
        for (Atom atom : atoms)
            init(parse_name, prompt, atom);

        for (int intejer : ints)
            init(parse_name, prompt, intejer);

        for (Float flote : floats)
            init(parse_name, prompt, flote);

        for (Double dubble : doubles)
            init(parse_name, prompt, dubble);
    }

    public InputSpec(String parse_name, String prompt, SubVarArgs... parsed_values) {

        // SubVarArgs should be a typedef, if that were possible

        for (SubVarArgs sub_var_args : parsed_values) {

            if (sub_var_args.type_ish() == Atom.class)
                for (Atom atom : (Atom[]) sub_var_args.array)
                    init(parse_name, prompt, atom);

            if (sub_var_args.type_ish() == Integer.class)
                for (Integer intejer : (Integer[]) sub_var_args.array)
                    init(parse_name, prompt, intejer);

            if (sub_var_args.type_ish() == Float.class)
                for (Float flote : (Float[]) sub_var_args.array)
                    init(parse_name, prompt, flote);

            if (sub_var_args.type_ish() == Double.class)
                for (Double dubble : (Double[]) sub_var_args.array)
                    init(parse_name, prompt, dubble);
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

    void init(String parse_name, String prompt, int num_values) {
        this.parse_name = parse_name;
        this.prompt = prompt;
        this.parsed_values = new Atom[num_values];
    }

    /***************************************************************************/

    public String sprint() {
        String out = parse_name + " = ";

        int i = 0;
        for (; i < parsed_values.length - 1; i++)
            out += parsed_values[i].value.toString() + " ";

        out += parsed_values[i].value.toString();

        return out;
    }

    public static String array_sprint(InputSpec[] specs) {
        String out = "";

        int i = 0;
        for (; i < specs.length - 1; i++)
            out += specs[i].sprint() + "\n";

        out += specs[i].sprint();

        return out;
    }
}
