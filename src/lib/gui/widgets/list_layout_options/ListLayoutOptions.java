package lib.gui.widgets.list_layout_options;

import gui.layout_options.*;

public class ListLayoutOptions extends NativeLayoutOptions {

    // These spacing options are mutually exclusive

    static public double INACTIVE = 1;

    public double space = INACTIVE;
    public double flex_weight = INACTIVE;

    public ListLayoutOptions() {};

    public ListLayoutOptions(LayoutOption... options) {
        parse_options(options);
    }

    public void parse_options(LayoutOption... options) {
        for (int i = 0; i < options.length; i++) {
            if (!parse_option(options[i]))
                throw new RuntimeException("ListLayoutOptions: parse_options: Encountered invalid option.");
        }
    }

    public boolean parse_option(LayoutOption option) {
        if (option instanceof ListLayoutOption)
            ((ListLayoutOption) option).write_to_container(this);

        if (super.parse_option(option))
            return true;

        return false;
    }

    public void copy(ListLayoutOptions options) {
        super.copy(this);
        space = options.space;
        flex_weight = options.flex_weight;
    }

    NativeLayoutOptions get_native_wrappers() {
        return this;
    }
}
