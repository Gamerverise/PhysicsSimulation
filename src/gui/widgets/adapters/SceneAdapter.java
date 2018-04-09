package gui.widgets.adapters;

import lib.widgets.ParentWidget;
import lib.widgets.Widget;
import javafx.scene.layout.Region;

public class SceneAdapter extends Region {

    public Widget root_widget;

    public SceneAdapter(Widget root_widget) {
        this.root_widget = root_widget;
    }

    protected double computeMinWidth(double height) {
        return 0;
    }

    protected double computeMinHeight(double width) {
        return 0;
    }

    protected double computePrefWidth(double height) {
        return 0;
    }

    protected double computePrefHeight(double width) {
        return 0;
    }

    protected void layoutChildren() {
        root_widget.set_geometry(0, 0, getWidth(), getHeight());

        if (root_widget instanceof ParentWidget)
            ((ParentWidget) root_widget).layout();
    }
}
