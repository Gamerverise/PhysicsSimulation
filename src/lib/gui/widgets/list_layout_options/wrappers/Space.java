package gui.widgets.list_layout_options.wrappers;

import gui.layout_options.*;
import gui.widgets.list_layout_options.*;

public class Space extends ListLayoutOption {

    public double space;

    public Space(double space) {
        this.space = space;
    }

    protected void write_to_container(ListLayoutOptions container) {
        container.space = space;
    }
}
