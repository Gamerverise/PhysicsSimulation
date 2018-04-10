package lib.widgets;

public abstract class Widget {

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

    public void layout_box(double x1, double x2, double y1, double y2) {
        layout(x1, y1, Math.abs(x2 - x1), Math.abs(y2 - y1));
    }

    public void draw_self() {}

    public void layout() {}
}
