package lib.widget.adapters;

import javafx.scene.Node;
import javafx.scene.layout.Region;
import lib.widget.RootWidget;
import lib.widget.Widget;

public class SceneAdapter extends Region {

    public RootWidget root_widget;
    public Node root_node;

    public SceneAdapter(RootWidget root_widget) {
        this.root_widget = root_widget;
        this.root_node = root_widget.get_javafx_root();

        getChildren().add(root_node);
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
        root_widget.layout(0, 0, getWidth(), getHeight());
    }
}
