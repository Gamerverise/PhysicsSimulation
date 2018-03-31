package lib.gui.layout_options.native_wrappers;

import javafx.geometry.Insets;

import gui.layout_options.*;

public class MARGIN extends NativeLayoutOption {

    Insets insets;

    public MARGIN(Insets insets) {
        this.insets = insets;
    }

    public MARGIN(double top, double left, double bottom, double right) {

        // Margin constructor arguments in my conventional order,
        // but Insets conventional order is different.

        this.insets = new Insets(top, right, bottom, left);
    }

    public void write_to_container(NativeLayoutOptions container) {
        container.margin = new Insets(insets.getTop(), insets.getRight(), insets.getBottom(), insets.getLeft());
    }
}
