package lib.widget.adapters;

import javafx.scene.layout.Region;
import lib.javafx_api_extensions.SceneX;
import lib.widget.ParentWidget;
import lib.widget.Widget;

public class RootAdapter {

    public AdapterRegionSide adapter_region_side;
    public AdapterWidgetSide adapter_widget_side;

    public RootAdapter(Widget... children) {
        adapter_widget_side = new AdapterWidgetSide(children);
        adapter_region_side = new AdapterRegionSide();
    }

    public class AdapterRegionSide extends Region {

        protected double computeMinWidth(double height) {
            return adapter_widget_side.width;
        }

        protected double computeMinHeight(double width) {
            return adapter_widget_side.height;
        }

        protected double computePrefWidth(double height) {
            return adapter_widget_side.width;
        }

        protected double computePrefHeight(double width) {
            return adapter_widget_side.height;
        }

        protected void layoutChildren() {
            adapter_widget_side.layout(0, 0, adapter_region_side.getWidth(), getHeight());
        }
    }

    public class AdapterWidgetSide extends ParentWidget {

        public AdapterWidgetSide(Widget[] children) {
            super(children);
        }

        public RootAdapter get_root() {
            return RootAdapter.this;
        }
    }
}
