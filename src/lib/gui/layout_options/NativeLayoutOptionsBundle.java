package lib.gui.layout_options;

import javafx.scene.layout.Region;

public class NativeLayoutOptionsBundle extends LayoutOptionsBundle {

    public NativeLayoutOptions options;

    NativeLayoutOptionsBundle(Region region) {
        super(region);
        options = new NativeLayoutOptions();
    }

    NativeLayoutOptionsBundle(Region region, LayoutOption... options) {
        super(region);
        this.options = new NativeLayoutOptions(options);
    }

    public NativeLayoutOptions get_native_wrappers() {
        return this.options;
    }
}
