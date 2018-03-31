package gui.widgets;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;

import gui.multi_class.*;
import gui.layout_options.*;
import gui.jfx_extensions.*;
import java_lang_extensions.multi_class.*;

import static javafx.scene.paint.Color.RED;

public class CoordinatesWidget extends HorizontalListWidget implements Descendant {

    /***** Multi-class ****************************************/

    DescendantDispatcher dispatcher = new DescendantDispatcher(
            new _AEMBOT_GUI_Ancestor(this)
    );

    /**********************************************************/

    Label x_entry;
    Label y_entry;
    Label theta_entry;

    FontX font;

    double font_size = 50;

    CoordinatesWidget() {
        set_entries("(", "-, ", "-, ", "-", ")");
setBackground(new Background(new BackgroundFill(RED, null, null)));

        x_entry = (Label) entries[1];
        y_entry = (Label) entries[2];
        theta_entry = (Label) entries[3];

        font = new FontX(x_entry.getFont(), font_size);

        ((Label) entries[0]).setFont(font.font);
        x_entry.setFont(font.font);
        y_entry.setFont(font.font);
        theta_entry.setFont(font.font);
        ((Label) entries[4]).setFont(font.font);
    }

    void init_entry(int i) {
        Object entry = entries[i];

        if (entry instanceof String) {
            Label label = new Label((String) entry);
            entries[i] = label;
            getChildren().add(label);
            return;
        }

        super.init_entry(i);
    }

    public void set_coords(double x, double y, double theta) {
        String x_str = Double.isNaN(x) ? "-," : x + ",";
        String y_str = Double.isNaN(y) ? "-," : y + ",";
        String theta_str = Double.isNaN(theta) ? "-," : theta + ",";

        x_entry.setText(x_str);
        y_entry.setText(y_str);
        theta_entry.setText(theta_str);
    }
}
