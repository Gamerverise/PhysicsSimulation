package gui.widgets;

public abstract class Widget {

    public double x;
    public double y;
    public double width;
    public double height;

    public Widget[] children;

    public Widget(Widget... children) {
        this.children = children;
    }

    public void layout() {
        // Each widget lays out its children. By default, the traversal of the widget
        // tree is breadth-first. A widget can override the traversal order by
        // overriding this method

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        layout_children();

        for (Widget child : children)
            child.layout();
    }

    public abstract void layout_children();

    public void draw() {
        // Widgets are drawn in depth-first order so that a parent can draw over its
        // children

        for (Widget child : children)
            child.draw();

        draw_self();
    }

    public abstract void draw_self();
}
