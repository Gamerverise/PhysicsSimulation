package lib.gui.layout_options.native_wrappers;

import gui.layout_options.*;

public class FILL_HEIGHT extends NativeLayoutOption {

    boolean flag;

    public FILL_HEIGHT(boolean flag) {
        this.flag = flag;
    }

    public void write_to_container(NativeLayoutOptions container) {
        container.fill_height = flag;
    }
}
