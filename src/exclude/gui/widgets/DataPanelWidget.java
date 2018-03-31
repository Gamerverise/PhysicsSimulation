package gui.widgets;

import com.sun.javafx.scene.control.skin.TitledPaneSkin;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;

import java_lang_extensions.multi_class.*;
import gui.multi_class.*;

import static gui.GUI.widget_spacing;
import static gui.layout_options.Layout.*;
import static gui.layout_options.NativeLayoutOption.*;
import static gui.widgets.ListWidget.*;
import static gui.widgets.list_layout_options.ListLayoutOption.*;

public class DataPanelWidget extends TitledPane implements Descendant
{
    /***** Multi-class ****************************************/

    DescendantDispatcher dispatcher = new DescendantDispatcher(
            new _AEMBOT_GUI_Ancestor(this)
    );

    public <T extends _Ancestor> T dispatch() {
        return dispatcher.dispatch();
    }

    /**********************************************************/

    HorizontalListWidget list;

    CoordinatesWidget coords;
    BatteryVoltageGraphWidget voltage;
    Label blank;

    VerticalListWidget coords_section;
    VerticalListWidget voltage_section;
    VerticalListWidget blank_section;

    DataPanelWidget() {
        setText("Readouts");

        coords = new CoordinatesWidget();
        voltage = new BatteryVoltageGraphWidget();
        blank = new Label("Intentionally blank.");

        coords_section = new VerticalListWidget(
                widget_spacing,
                "Coordinates (x, y, theta)",
                Layout(coords, FILL_WIDTH(false), FlexSize()));

        voltage_section = new VerticalListWidget(
                widget_spacing,
                "Battery Voltage",
                Layout(voltage, FILL_WIDTH(false), FlexSize()));

        blank_section = new VerticalListWidget(
                widget_spacing,
                "Future Features",
                Layout(blank, FlexSize()));

        blank = new Label("Intentionally blank.");

        list = new HorizontalListWidget(
                widget_spacing,
                coords_section,
                widget_spacing,
                '|',
                widget_spacing,
                voltage_section,
                widget_spacing,
                '|',
                Layout(blank_section, FlexSize())
        );

        setContent(list);
    }

    protected void layoutChildren() {
        super.layoutChildren();

        layoutInArea(list,
                0, 0,
                // TitledPaneSkin may have a bug where the content container is wider than TitledPane
                ((TitledPaneSkin)getSkin()).getContentContainer().getWidth(),
                ((TitledPaneSkin)getSkin()).getContentContainer().getHeight(),
                0, Insets.EMPTY, false, false, HPos.CENTER, VPos.CENTER);
    }
}
