package lib.gui.widgets.list_layout_options;

import javafx.scene.layout.Region;

import lib.gui.layout_options.*;

public class ListLayoutOptionsBundle extends LayoutOptionsBundle {

    public ListLayoutOptions options;

    public ListLayoutOptionsBundle(Region region) {
        super(region);
        options = new ListLayoutOptions();
    }

    public ListLayoutOptionsBundle(Region region, LayoutOption... options) {
        super(region);
        this.options = new ListLayoutOptions(options);
    }

    public NativeLayoutOptions get_native_wrappers() {
        return this.options;
    }
}
