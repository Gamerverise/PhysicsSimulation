package lib.gui.layout_options;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;

import gui.layout_options.native_wrappers.*;

abstract public class NativeLayoutOption extends LayoutOption {

    public static AREA_BASELINE_OFFSET AREA_BASELINE_OFFSET(double offset) {
        return new AREA_BASELINE_OFFSET(offset);
    }

    public static MARGIN MARGIN(double top, double left, double bottom, double right) {
        return new MARGIN(top, left, bottom, right);
    }

    public static MARGIN MARGIN(Insets insets) {
        return new MARGIN(insets);
    }

    public static FILL_WIDTH FILL_WIDTH(boolean flag) {
        return new FILL_WIDTH(flag);
    }

    public static FILL_HEIGHT FILL_HEIGHT(boolean flag) {
        return new FILL_HEIGHT(flag);
    }

    public static H_ALIGNMENT H_ALIGNMENT(HPos h_pos) {
        return new H_ALIGNMENT(h_pos);
    }

    public static V_ALIGNMENT V_ALIGNMENT(VPos v_pos) {
        return new V_ALIGNMENT(v_pos);
    }

    public static SNAP_TO_PIXEL SNAP_TO_PIXEL(boolean flag) {
        return new SNAP_TO_PIXEL(flag);
    }

    abstract protected void write_to_container(NativeLayoutOptions container);
}
