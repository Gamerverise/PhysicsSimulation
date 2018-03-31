package gui.layout_options.native_wrappers;

import gui.layout_options.*;

public class SNAP_TO_PIXEL extends NativeLayoutOption {

    boolean flag;

    public SNAP_TO_PIXEL(boolean flag) {
        this.flag = flag;
    }

    public void write_to_container(NativeLayoutOptions container) {
        container.snap_to_pixel = flag;
    }
}
