package gui.jfx_extensions;

import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import static javafx.scene.text.FontPosture.REGULAR;
import static javafx.scene.text.FontWeight.NORMAL;

public class FontX {
    public Font font;

    public FontX(Font font, double size) {
        this.font = Font.font(font.getFamily(), NORMAL, REGULAR, size);
    }
}
