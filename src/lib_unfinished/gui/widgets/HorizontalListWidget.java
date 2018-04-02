package gui.widgets;

import javafx.geometry.Orientation;

import javafx.scene.control.Separator;
import javafx.scene.layout.Region;

import gui.multi_class.*;
import gui.layout_options.*;
import gui.widgets.list_layout_options.*;

import static javafx.geometry.Orientation.VERTICAL;

import static gui.layout_options.LayoutOptions.default_layout_in_area;

public class HorizontalListWidget extends ListWidget {

    HorizontalListWidget(Object... entries) {
        super(entries);
    }

    protected double computePrefWidth(double height) {
        return compute_pref_list_distance(height);
    }

    void init_entry(int i) {
        Object entry = entries[i];

        if (entry instanceof Character)
            if ((Character)entry == '|') {
                Region region = new Separator(VERTICAL);
                entries[i] = region;
                getChildren().add(region);
                return;
            }

        super.init_entry(i);
    }

    void layout_in_area(ListLayoutOptionsBundle bundle,
                        double ortho_coord, double list_coord,
                        double ortho_distance, double list_distance)
    {
        bundle.layout_in_area(list_coord, ortho_coord, list_distance, ortho_distance);
    }
    void layout_in_area(Region region,
                        double ortho_coord, double list_coord,
                        double ortho_distance, double list_distance)
    {
        default_layout_in_area(region, list_coord, ortho_coord, list_distance, ortho_distance);
    }

    double get_child_pref_distance(Region region, double ortho_distance) {
        return region.prefWidth(ortho_distance);
    }

    double get_list_distance() {
        return getWidth();
    }

    double get_ortho_distance() {
        return getHeight();
    }

    void set_list_distance(double length) {
        setWidth(length);
    }

    void set_ortho_distance(double length) {
        setHeight(length);
    }
}
