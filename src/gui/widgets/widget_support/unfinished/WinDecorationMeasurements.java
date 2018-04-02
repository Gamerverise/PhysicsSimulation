package gui.widgets.widget_support.unfinished;

import javafx.stage.Window;
import lib_2.java_lang_extensions.anonymous_types.Struct;
import lib_2.java_lang_extensions.overload_extensions.OverloadExtension;

import static lib_2.java_lang_extensions.anonymous_types.StructFieldName.*;

public class WinDecorationMeasurements extends Struct<Object> {

    public WinDecorationMeasurements(Window win, OverloadExtension.UNKNOWN sig_keyword) {
        // -1 represents that the thicknesses are unknown

        put(TOP, -1,
            LEFT, -1,
            BOTTOM, -1,
            RIGHT, -1);
    }

    public WinDecorationMeasurements(Window win, OverloadExtension.HEURISTIC sig_keyword) {
        put(TOP, 15 * 5,    // Heuristic for top
            LEFT, 15,       // Heuristic for left
            BOTTOM, 150,    // Heuristic for bottom
            RIGHT, 15);     // Heuristic for right
    }

    public WinDecorationMeasurements(double top, double left, double bottom, double right) {
        put(TOP, top,
            LEFT, left,
            BOTTOM, bottom,
            RIGHT, right);
    }

    boolean is_unknown() {
        // FIXME
        Integer x = get(TOP);
        return true;
//        return get<Integer>(TOP) < 0 || left < 0 || bottom < 0 || right < 0;
    }

    void put(double top, double left, double bottom, double right) {
        put(TOP, top,
            LEFT, left,
            BOTTOM, bottom,
            RIGHT, right);
    }
}
