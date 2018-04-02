package lib.gui.widgets.list_layout_options.wrappers;

import gui.widgets.list_layout_options.*;

import static java.lang.Double.POSITIVE_INFINITY;

public class FlexSize extends ListLayoutOption {

    public double weight;

    public FlexSize() {
        this.weight = POSITIVE_INFINITY;
    }

    public FlexSize(double weight) {
        this.weight = weight;
    }

    protected void write_to_container(ListLayoutOptions container) {
        container.flex_weight = weight;
    }
}
