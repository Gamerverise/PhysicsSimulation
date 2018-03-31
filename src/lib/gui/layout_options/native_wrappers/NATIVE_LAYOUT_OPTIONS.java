package lib.gui.layout_options.native_wrappers;

import gui.layout_options.*;

public class NATIVE_LAYOUT_OPTIONS extends NativeLayoutOption {
    NativeLayoutOptions options;

    public NATIVE_LAYOUT_OPTIONS(NativeLayoutOptions options) {
        this.options.copy(options);
    }

    public void write_to_container(NativeLayoutOptions container) {
        container.copy(this.options);
    }
}
