package lib.gui.layout_options.native_wrappers;

import javafx.geometry.HPos;

import gui.layout_options.*;

public class H_ALIGNMENT extends NativeLayoutOption {

    HPos h_pos;

    public H_ALIGNMENT(HPos h_pos) {
        this.h_pos = h_pos;
    }

    public void write_to_container(NativeLayoutOptions container) {
        container.h_alignment = h_pos;
    }
}
