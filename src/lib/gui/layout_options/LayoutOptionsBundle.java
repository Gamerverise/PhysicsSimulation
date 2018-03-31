package gui.layout_options;

import javafx.scene.layout.Region;

abstract public class LayoutOptionsBundle {

    public Region region;

    public LayoutOptionsBundle(Region region) {
        this.region = region;
    }

    abstract public NativeLayoutOptions get_native_wrappers();

    public void layout_in_area(double x, double y, double width, double height) {
        get_native_wrappers().layout_in_area(region, x, y, width, height);
    }
}
