package gui.widgets;

public abstract class ParentWidget extends Widget {

    public Widget[] children;

    public ParentWidget(Widget[] children) {
        this.children = children;
    }

    public void layout() {
        // Each widget lays out its children. By default, the traversal of the widget
        // tree is breadth-first. A widget can override the traversal order by
        // overriding this method

        layout_children();

        for (Widget child : children)
            if (child instanceof ParentWidget)
                ((ParentWidget) child).layout();
    }

    public void layout_children() {}

    public void draw() {
        // Widgets are drawn in depth-first order so that a parent can draw over its
        // children

        for (Widget child : children)
            if (child instanceof ParentWidget)
                ((ParentWidget) child).draw();

        draw_self();
    }
}
