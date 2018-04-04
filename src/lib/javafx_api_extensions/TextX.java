package lib.javafx_api_extensions;

import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import static javafx.scene.text.FontPosture.REGULAR;
import static javafx.scene.text.FontWeight.NORMAL;

public class TextX extends Text {
    public TextX(String l, Font f) {
        super(l);
        setFont(f);
    }

    public double get_typeset_width() {
        return getLayoutBounds().getWidth();
    }

    public double get_typeset_height() {
        return getLayoutBounds().getHeight();
    }

    public static void text_set_font_size_for_rectangle(
            Text text,
            FontWeight weight,
            FontPosture posture,
            double width,
            double height)
    {
        // Logarithmic search for font size

        int max_iterations = 16;

        String family = text.getFont().getFamily();

        if (weight == null)
            weight = NORMAL;

        if (posture == null)
            posture = REGULAR;

        double last_fit_size = -1;
        double cur_upper_bound = 500;
        double cur_lower_bound = 0;
        double cur_size = (cur_upper_bound - cur_lower_bound) / 2;

        for (int i = 0; i < max_iterations; i++) {

            text.setFont(Font.font(family, weight, posture, cur_size));

            boolean width_less = text.getLayoutBounds().getWidth() < width;
            boolean height_less = text.getLayoutBounds().getHeight() < height;

            if (width_less && height_less) {
                last_fit_size = cur_size;
                cur_lower_bound = cur_size;
            } else
                cur_upper_bound = cur_size;

            cur_size = (cur_upper_bound - cur_lower_bound) / 2;
        }

        text.setFont(Font.font(family, weight, posture, last_fit_size));
    }
}
