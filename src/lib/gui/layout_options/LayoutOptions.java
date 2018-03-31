package gui.layout_options;

import javafx.scene.layout.Region;

abstract public class LayoutOptions {

    static NativeLayoutOptions DEFAULT_NATIVE_LAYOUT_OPTIONS = new NativeLayoutOptions();

    abstract NativeLayoutOptions get_native_wrappers();

    public void layout_in_area(Region region, double x, double y, double width, double height) {
        NativeLayoutOptions options = get_native_wrappers();

        Region.layoutInArea(
                region,
                x, y,
                width, height,
                options.area_baseline_offset,
                options.margin,
                options.fill_width, options.fill_height,
                options.h_alignment, options.v_alignment,
                options.snap_to_pixel);
    }

    static public void default_layout_in_area(Region region, double x, double y, double width, double height) {
        Region.layoutInArea(
                region,
                x, y,
                width, height,
                DEFAULT_NATIVE_LAYOUT_OPTIONS.      area_baseline_offset,
                DEFAULT_NATIVE_LAYOUT_OPTIONS.      margin,
                DEFAULT_NATIVE_LAYOUT_OPTIONS.      fill_width,
                DEFAULT_NATIVE_LAYOUT_OPTIONS.      fill_height,
                DEFAULT_NATIVE_LAYOUT_OPTIONS.      h_alignment,
                DEFAULT_NATIVE_LAYOUT_OPTIONS.      v_alignment,
                DEFAULT_NATIVE_LAYOUT_OPTIONS.      snap_to_pixel
        );
    }
}
