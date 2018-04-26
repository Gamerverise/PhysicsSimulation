package lib.widget;

import lib.widget.adapters.RootAdapter;

public abstract class Widget {

    ParentWidget parent;

    public double x;
    public double y;
    public double width;
    public double height;

    public void set_geometry(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void set_geometry_box(double x1, double x2, double y1, double y2) {
        set_geometry(x1, y1, Math.abs(x2 - x1), Math.abs(y2 - y1));
    }

    public void layout(double x, double y, double width, double height) {
        set_geometry(x, y, width, height);
        layout();
    }

    @Deprecated
    public void layout_box(double x1, double x2, double y1, double y2) {
        layout(x1, y1, Math.abs(x2 - x1), Math.abs(y2 - y1));
    }

    public void draw_self() {}

    public void layout() {}

    public RootAdapter get_root() {
        return parent.get_root();
    }

    public void layout_before_next_frame() {
        get_root().adapter_region_side.requestLayout();
    }
}
