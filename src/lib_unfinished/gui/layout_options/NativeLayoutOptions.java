package lib.gui.layout_options;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.layout.Region;

import gui.layout_options.native_wrappers.*;

public class NativeLayoutOptions extends LayoutOptions {

    public double area_baseline_offset = 0;
    public Insets margin = Insets.EMPTY;
    public boolean fill_width = true;
    public boolean fill_height = true;
    public HPos h_alignment = HPos.CENTER;
    public VPos v_alignment = VPos.CENTER;
    public boolean snap_to_pixel = false;

    NativeLayoutOptions() {}

    public NativeLayoutOptions(LayoutOption... options) {
        parse_options(options);
    }

    public void parse_options(LayoutOption... options) {
        for (int i = 0; i < options.length; i++) {
            if (!parse_option(options[i]))
                throw new RuntimeException("NativeLayoutOptions: parse_options: Encountered invalid option.");
        }
    }

    public boolean parse_option(LayoutOption option) {
        if (option instanceof NativeLayoutOption) {
            ((NativeLayoutOption) option).write_to_container(this);
            return true;
        }

        return false;
    }

    public void copy(NativeLayoutOptions options) {
        area_baseline_offset = options.area_baseline_offset;
        margin = options.margin;
        fill_width = options.fill_width;
        fill_height = options.fill_height;
        h_alignment = options.h_alignment;
        v_alignment = options.v_alignment;
        snap_to_pixel= options.snap_to_pixel;
    }

    NativeLayoutOptions get_native_wrappers() {
        return this;
    }
}
