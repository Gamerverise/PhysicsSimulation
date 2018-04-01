package gui.widgets.widget_support;

import javafx.stage.Window;
import lib_2.java_lang_extensions.anonymous_types.Struct;

import static lib_2.java_lang_extensions.anonymous_types.StructField.*;

public class WinDecorationInfo extends Struct {

    public enum Unknown {UNKNOWN};
    public enum Heuristic {HEURISTIC};

    public WinDecorationInfo(Window win, WinDecorationInfo.Unknown unknown) {
        // -1 represents that the thicknesses are unknown

        put(TOP, -1);
        put(LEFT, -1);
        put(BOTTOM, -1);
        put(RIGHT, -1);
    }

    public WinDecorationInfo(Window win, WinDecorationInfo.Heuristic heuristic) {
        put(TOP, 15 * 5);   // Heuristic for top
        put(LEFT, 15);      // Heuristic for left
        put(BOTTOM, 150);    // Heuristic for bottom
        put(RIGHT, 15);     // Heuristic for right
    }

    WinDecorationInfo(double top, double left, double bottom, double right) {
        put(TOP, top,
            LEFT, left,
            BOTTOM, bottom,
            RIGHT, right);
    }

    boolean is_unknown() {
        return get<Integer>(TOP) < 0 || left < 0 || bottom < 0 || right < 0;
    }

    void set(double top, double left, double bottom, double right) {
        put(TOP, top);
        this.left = left;
        this.bottom = bottom;
        this.right = right;
    }
}
