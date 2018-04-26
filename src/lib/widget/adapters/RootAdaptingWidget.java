package lib.widget.adapters;

import lib.widget.ParentWidget;
import lib.widget.Widget;

class RootAdaptingWidget extends ParentWidget {

    RootAdaptingRegion root_adapting_region;

    public RootAdaptingRegion get_root_adapting_region(RootAdaptingRegion root_adapting_region, Widget[] children) {
        super(children);
        this.root_adapting_region = root_adapting_region;
    }

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
