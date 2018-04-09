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
        this.x = x1;
        this.y = y1;
        this.width = Math.abs(x2 - x1);
        this.height = Math.abs(y2 - y1);
    }

    public abstract void draw_self();

    public abstract void set_
}
