package gui.widgets;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import gui.jfx_extensions.*;

public class LogoWidget extends Group {
    ImageView logo_background;
    Text logo_text =  new Text("AEMBOT");
    Text logo_subtext = new Text("Liberty Highschool, Hillsboro, Oregon");

    protected void layout(double width, double height) {
        // FIXME: The logo may not look good in certain situations. Additional programming required at least
        // for very thin bottom logos and thin right-side logos.

        double text_percent = 0.6;
        double subtext_percent = 0.3;

//        logo_background.setLayoutX(0);
//        logo_background.setLayoutY(0);

        TextX.text_set_font_size_for_rectangle(logo_text, null, null, width, text_percent * height);
        TextX.text_set_font_size_for_rectangle(logo_subtext, null, null, width, subtext_percent * height);

        logo_text.setLayoutX((width - logo_text.getLayoutBounds().getWidth()) / 2);
        logo_text.setLayoutY(0);

        logo_subtext.setLayoutX((width - logo_subtext.getLayoutBounds().getWidth()) / 2);
        logo_subtext.setLayoutY((1 - subtext_percent)*height);
    }

}