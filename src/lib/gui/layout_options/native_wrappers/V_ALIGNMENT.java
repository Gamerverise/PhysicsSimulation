package lib.gui.layout_options.native_wrappers;

import javafx.geometry.VPos;

import gui.layout_options.*;

public class V_ALIGNMENT extends NativeLayoutOption {

    VPos v_pos;

    public V_ALIGNMENT(VPos v_pos) {
        this.v_pos = v_pos;
    }

    public void write_to_container(NativeLayoutOptions container) {
        container.v_alignment = v_pos;
    }
}
