package lib.gui.layout_options;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.layout.Region;

import static gui.layout_options.LayoutOption.*;

abstract public class LayoutBundle<T extends NativeLayoutOptions> {

    public Region region;
    public T options;

    public LayoutBundle(Region region) {
        this.region = region;
    }
//FIXME
    abstract NativeLayoutOptions get_native_wrappers();

    public void layout_in_area(double x, double y, double width, double height) {
        get_native_wrappers().layout_in_area(region, x, y, width, height);
    }
}
