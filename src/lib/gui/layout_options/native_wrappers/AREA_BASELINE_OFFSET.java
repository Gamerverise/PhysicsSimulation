package lib.gui.layout_options.native_wrappers;

import lib.gui.layout_options.*;

public class AREA_BASELINE_OFFSET extends NativeLayoutOption {

    double offset;

    public AREA_BASELINE_OFFSET(double offset) {
        this.offset = offset;
    }

    public void write_to_container(NativeLayoutOptions container) {
        container.area_baseline_offset = offset;
    }
}
