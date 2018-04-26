package lib.widget;

public abstract class ParentWidget extends Widget {

    public Widget[] children;

    public ParentWidget(Widget[] children) {
        this.children = children;
    }

    public void layout() {
        // Most or all sub-classes of ParentWidget should override this method. This method is here only
        // for completeness. Since this method does not change the geometry of any of its children,
        // a child will not change unless it does something independent of a change in its geometry.

        layout_children();
    }

    public void layout_children() {
        for (Widget child : children)
            child.layout();
    }

    public void draw() {
        // Widgets are drawn in depth-first order so that a parent can draw over its
        // children

        for (Widget child : children)
            if (child instanceof ParentWidget)
                ((ParentWidget) child).draw();

        draw_self();
    }
}
