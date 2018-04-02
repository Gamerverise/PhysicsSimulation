package lib.gui.widgets.list_layout_options;

import lib.gui.layout_options.*;
import lib.gui.widgets.list_layout_options.wrappers.*;

abstract public class ListLayoutOption extends LayoutOption {

    static public final Space Space(double space) {
        return new Space(space);
    }

    static public final FlexSize FlexSize() {
        return new FlexSize(Double.MAX_VALUE);
    }

    static public final FlexSize FlexSize(double weight) {
        return new FlexSize(weight);
    }

    abstract protected void write_to_container(ListLayoutOptions container);
}
