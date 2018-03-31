package gui.layout_options.native_wrappers;

import gui.layout_options.*;

public class FILL_WIDTH extends NativeLayoutOption {

    boolean flag;

    public FILL_WIDTH(boolean flag) {
        this.flag = flag;
    }

    public void write_to_container(NativeLayoutOptions container) {
        container.fill_width = flag;
    }
}
