package lib.render;

import javafx.scene.canvas.Canvas;

import lib.debug.MethodNameHack;
import lib.javafx_api_extensions.javafx_support.ScaleOp;
import lib.tokens.enums.WidthHeightToken;

import static lib.debug.AssertMessages.BAD_CODE_PATH;
import static lib.debug.Debug.assert_msg;

public class View {

    public double dx;
    public double dy;
    public double scale;

    public View() {
        dx = 0;
        dy = 0;
        scale = 1;
    }

    public View(double dx, double dy, double scale) {
        this.dx = dx;
        this.dy = dy;
        this.scale = scale;
    }
}
