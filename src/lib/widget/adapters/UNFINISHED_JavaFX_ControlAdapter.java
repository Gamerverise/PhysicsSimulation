package lib.widget.adapters;

import javafx.scene.control.Control;
import lib.widget.Widget;

public class UNFINISHED_JavaFX_ControlAdapter extends Widget {

    public Control control;

    public UNFINISHED_JavaFX_ControlAdapter(Control control) {
        this.control = control;
    }

// FIXME: Gotta think about the uses for this code
//
//    public static void set_node_geometry(BinaryNode link, double x, double y, double width, double height) {
//        if (link.getContentBias() == HORIZONTAL) {
//            link.minHeight(height);
//            link.prefHeight(height);
//            link.maxHeight(height);
//        } else if (link.getContentBias() == VERTICAL) {
//            link.minWidth(width);
//            link.minWidth(width);
//            link.minWidth(width);
//        }
//
//        link.resizeRelocate(x, y, width, height);
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
