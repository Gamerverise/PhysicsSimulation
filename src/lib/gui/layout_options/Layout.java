package gui.layout_options;

import javafx.scene.layout.Region;

public class Layout {

    public Region region;
    public LayoutOption[] options;

    public Layout(Region region, LayoutOption... options) {
        this.region = region;
        this.options = options;
    }

    static public Layout Layout(Region region, LayoutOption... options) {
        return new Layout(region, options);
    }
}
