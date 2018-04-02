package gui.widgets;

import javafx.scene.control.Separator;
import javafx.scene.layout.Region;

import gui.multi_class.*;
import gui.layout_options.*;
import gui.widgets.list_layout_options.*;

import static gui.GUI.widget_spacing;
import static gui.layout_options.LayoutOptions.default_layout_in_area;
import static javafx.geometry.Orientation.HORIZONTAL;

public class VerticalListWidget extends ListWidget {

    public static final double touchup_spacing = widget_spacing + 7;

    VerticalListWidget(Object... entries) {
        super(entries);
    }

    protected double computePrefHeight(double width) {
        return compute_pref_list_distance(width);
    }

    void init_entry(int i) {
        Object entry = entries[i];

        if (entry instanceof Character) {
            if ((Character) entry == '-') {
                Region region = new Separator(HORIZONTAL);
                entries[i] = region;
                getChildren().add(region);
                return;
            }
        }

        if (entry instanceof String) {
            Region region = new LabeledSeparatorWidget((String) entry);
            entries[i] = region;
            getChildren().add(region);
            return;
        }

        if (entry instanceof CustomLayout) {
            CustomLayout layout = (CustomLayout) entry;

            if (layout.object instanceof String) {
                Region region = new LabeledSeparatorWidget((String) layout.object);
                entries[i] = new ListLayoutOptionsBundle(region, layout.options);
                getChildren().add(region);
                return;
            }
        }

        super.init_entry(i);
    }

    void layout_in_area(ListLayoutOptionsBundle bundle,
                        double ortho_coord, double list_coord,
                        double ortho_distance, double list_distance)
    {
        bundle.layout_in_area(ortho_coord, list_coord, ortho_distance, list_distance);
    }

    void layout_in_area(Region region,
                      double ortho_coord, double list_coord,
                      double ortho_distance, double list_distance)
    {
        default_layout_in_area(region, ortho_coord, list_coord, ortho_distance, list_distance);
    }

    double get_child_pref_distance(Region region, double ortho_distance) {
        return region.prefHeight(ortho_distance);
    }

    double get_list_distance() {
        return getHeight();
    }

    double get_ortho_distance() {
        return getWidth();
    }

    void set_list_distance(double length) {
        setHeight(length);
    }

    void set_ortho_distance(double length) {
        setWidth(length);
    }
}
