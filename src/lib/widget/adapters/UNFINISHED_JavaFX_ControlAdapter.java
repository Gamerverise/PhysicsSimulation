package lib.widget.adapters;

import javafx.scene.Node;
import javafx.scene.layout.Region;
import lib.widget.Widget;

public class UNFINISHED_JavaFX_ControlAdapter extends Region {

    public RootWidget root_widget;
    public Node root_node;

    public UNFINISHED_JavaFX_ControlAdapter(RootWidget root_widget) {
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

package lib.widget.adapters;

        import javafx.scene.Node;
        import lib.widget.Widget;

class AdapterWidget extends Widget {

    // AdapterWidget is the base class for all our widgets, thus acting as an interface
    // between JavaFX and our widgets

    AdapterWidget[] children;

//    public void layout() {
//
//    }

    public abstract Node get_javafx_root();

    public SceneRootAdapter get_scene_root_adaper() {
        return this;
    }

// FIXME: Gotta think about the uses for this code

//    public static void set_node_geometry(Node node, double x, double y, double width, double height) {
//        if (node.getContentBias() == HORIZONTAL) {
//            node.minHeight(height);
//            node.prefHeight(height);
//            node.maxHeight(height);
//        } else if (node.getContentBias() == VERTICAL) {
//            node.minWidth(width);
//            node.minWidth(width);
//            node.minWidth(width);
//        }
//
//        node.resizeRelocate(x, y, width, height);
//    }
//
//    public static void set_region_geometry(Region r, double x, double y, double width, double height) {
//        r.setMinSize(width, height);
//        r.setPrefSize(width, height);
//        r.setMaxSize(width, height);
//
//        set_node_geometry(r, x, y, width, height);
//    }
}
