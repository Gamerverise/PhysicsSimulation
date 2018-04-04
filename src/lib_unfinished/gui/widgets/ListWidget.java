package gui.widgets;

import java.util.List;

import javafx.scene.Node;
import javafx.scene.layout.Region;

import java_lang_extensions.multi_class.*;
import gui.multi_class.*;
import gui.layout_options.*;
import gui.widgets.list_layout_options.*;
import gui.widgets.list_layout_options.wrappers.*;
import lib.debug.MethodNameHack;

import static java_lang_extensions.flow_control.FlowControl.*;
import static gui.widgets.list_layout_options.ListLayoutOption.*;
import static gui.widgets.list_layout_options.ListLayoutOptions.INACTIVE;

abstract public class ListWidget extends Region implements Descendant
{
    /***** Multi-class ****************************************/

    DescendantDispatcher dispatcher = new DescendantDispatcher(
            new _AEMBOT_GUI_Ancestor(this)
    );

    public <T extends _Ancestor> T dispatch() {
        return dispatcher.dispatch();
    }

    /**********************************************************/

    // ***********************************************

    /*
     * Types of Entries for Constructor and after Construction
     *     * Double - means Space
     *     * Space
     *     * FlexSize
     *     * Region - specifies a region that will use default layout options
     *     * ListLayoutOptionsBundle
     *
     * Additional Types for Constructor Only
     *     * LayoutBundle        // Will be converted to a ListLayoutOptionsBundle
     */

    Object[] entries;

    double flex_weight_total;

    ListWidget(Object... entries) {
        set_entries(entries);

        setBackground(debug.DebugGUI.corn_blue_bg);
    }

    void set_entries(Object... entries) {
        this.entries = entries;

        getChildren().setAll();

        double max_flex_weight = 0;
        int flex_entry_count = 0;

        for (int i = 0; i < entries.length; i++) {
            Object entry = this.init_entry(entries[i]);

            boolean has_flex = false;
            double flex_weight = 0;

            if (entry instanceof FlexSize) {
                flex_weight = ((FlexSize) entry).weight;
                has_flex = true;
            } else if (entry instanceof ListLayoutOptionsBundle) {
                flex_weight = ((ListLayoutOptionsBundle) entry).options.flex_weight;
                if (flex_weight != INACTIVE)
                    has_flex = true;

                has_flex = true;
            }

            if (has_flex) {
                if (flex_weight > max_flex_weight)
                    max_flex_weight = flex_weight;
                flex_entry_count++;
            }
        }

        // Scale the flex-space weight values, to avoid overflow erros

        double flex_weight_scale_factor = Double.MAX_VALUE / flex_entry_count;

        repeat (entries.length, (int i) ->
        {
            Object entry = entries[i];

            if (entry instanceof FlexSize) {
                FlexSize flex = (FlexSize) entry;
                flex.weight *= flex_weight_scale_factor;
            } else if (entry instanceof ListLayoutOptionsBundle) {
                ListLayoutOptionsBundle bundle = (ListLayoutOptionsBundle) entry;
                if (bundle.options.flex_weight != INACTIVE)
                    bundle.options.flex_weight *= flex_weight_scale_factor;
            }
        });
    }

    // Sub-classes may override init_entry to specialize the processing of entries

    Object init_entry(Object entry) {
        if (entry instanceof Double
                || entry instanceof Space
                || entry instanceof ListLayoutOptionsBundle)
            return this;

        if (entry instanceof FlexSize) {
// FIXME: HIGH PRIORITY         handle_flex_weight_info((FlexSize)entry);
            return this;

        } else if (entry instanceof Region) {
            getChildren().add((Region) entry);
            return this;

        } else if (entry instanceof Layout) {
            Layout layout = (Layout) entry;
            getChildren().add(layout.region);
            return new ListLayoutOptionsBundle(layout.region, layout.options);

        } else
            throw new RuntimeException("ListWidget: init_entry: Invalid type of entry.");
    }

    protected double compute_pref_list_distance(double ortho_distance) {
        double pref_distance = 0;

        for (int i = 0; i < entries.length; i++) {
            Object entry = entries[i];

            if (entry instanceof Double)
                pref_distance += (double)entry;

            if (entry instanceof Space)
                pref_distance += ((Space)entry).space;

            if (entry instanceof FlexSize)
                continue;

            else if (entry instanceof Region)
                pref_distance += get_child_pref_distance((Region)entry, ortho_distance);

            else if (entry instanceof ListLayoutOptionsBundle)
                pref_distance += get_child_pref_distance(((ListLayoutOptionsBundle)entry).region, ortho_distance);

            else
                throw new RuntimeException("ListWidget: compute_pref_list_distance: Invalid type of entry.");
        }

        return pref_distance;
    }

    protected void layoutChildren() {
        double ortho_distance = get_ortho_distance();
        double list_distance = get_list_distance();

        double pref_distance = compute_pref_list_distance(ortho_distance);
        double flex_distance = list_distance - pref_distance;

        // FIXME: HIGH PRIORITY: Do we need this? What is it for?
        // set_list_distance(get_list_distance() - 50);

        double cur_distance_coord = 0;

        for (int i = 0; i < entries.length; i++) {
            Object entry = entries[i];

            if (entry instanceof Double)
                cur_distance_coord += (Double)entry;

            else if (entry instanceof Space)
                cur_distance_coord += ((Space)entry).space;

            else if (entry instanceof FlexSize)
                cur_distance_coord += ((FlexSize)entry).weight / flex_weight_total * flex_distance;

            else if (entry instanceof Region) {
                Region region = (Region) entry;
                double entry_distance = get_child_pref_distance(region, ortho_distance);
                layout_in_area(region, 0, cur_distance_coord, ortho_distance, entry_distance);
                cur_distance_coord += entry_distance;

            } else if (entry instanceof ListLayoutOptionsBundle) {
                ListLayoutOptionsBundle bundle = (ListLayoutOptionsBundle) entry;
                ListLayoutOptions options = bundle.options;

                double entry_distance;

                assert (options.space == -1 || options.flex_weight == -1) : dbg_msg(
                        this.getClass(),
                        new MethodNameHack(){}.method_name(),
                        "space and flex_weight are mutually exclusive.");

                if (options.space > 0)
                    entry_distance = options.space;
                else if (options.flex_weight > 0)
                    entry_distance = options.flex_weight / flex_weight_total * flex_distance;
                else
                    entry_distance = get_child_pref_distance(bundle.region, ortho_distance);

                bundle.layout_in_area(0, cur_distance_coord, ortho_distance, entry_distance);

                cur_distance_coord += entry_distance;

            } else
                assert false : dbg_msg(
                        this.getClass(),
                        new MethodNameHack(){}.method_name(),
                        "Invalid type of entry.");
        }
    }

    abstract void layout_in_area(ListLayoutOptionsBundle bundle,
                                 double ortho_coord, double list_coord,
                                 double ortho_distance, double list_distance);

    abstract void layout_in_area(Region region,
                                 double ortho_coord, double list_coord,
                                 double ortho_distance, double list_distance);

    abstract double get_child_pref_distance(Region region, double ortho_distance);

    abstract double get_list_distance();

    abstract double get_ortho_distance();

    abstract void set_list_distance(double distance);

    abstract void set_ortho_distance(double distance);
}
