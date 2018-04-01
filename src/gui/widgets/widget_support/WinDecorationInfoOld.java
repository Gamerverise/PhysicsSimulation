package gui.widgets.widget_support;

public class WinDecorationInfoOld {

    public enum Unknown {UNKNOWN};
    public enum Heuristic {HEURISTIC};

    double top;
    double left;
    double bottom;
    double right;

    public WinDecorationInfoOld(WinDecorationInfoOld.Unknown unknown) {
        // -1 represents that the thicknesses are unknown

        top = -1;
        left = -1;
        bottom = -1;
        right = -1;
    }

    public WinDecorationInfoOld(WinDecorationInfoOld.Heuristic heuristic) {
        top = 15 * 5;   // Heuristic for top
        left = 15;      // Heuristic for left
        bottom = 15;    // Heuristic for bottom
        right = 15;     // Heuristic for right
    }

    WinDecorationInfoOld(double top, double left, double bottom, double right) {
        set(top, left, bottom, right);
    }

    boolean is_unknown() {
        return top < 0 || left < 0 || bottom < 0 || right < 0;
    }

    void set(double top, double left, double bottom, double right) {
        this.top = top;
        this.left = left;
        this.bottom = bottom;
        this.right = right;
    }
}
