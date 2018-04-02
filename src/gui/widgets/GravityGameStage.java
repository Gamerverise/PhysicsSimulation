package gui.widgets;

import gui.javafx_api_extensions.StageX;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class GravityGameStage extends StageX {

    GravityGameStage(Stage stage) {
        super(stage);

        // FIXME (maybe fixed): What is the proper/best order of operations for initialing a window? We may have the best/proper order
        // FIXME (maybe fixed): Does stage have to be showing when centerOnScreen is called? Apparently not

        double stage_width_px = 0.9 * Screen.getPrimary().getVisualBounds().getWidth();
        double stage_height_px = 0.9 * Screen.getPrimary().getVisualBounds().getHeight();

        stage.setTitle("Gravity Game");

        stage.setMinWidth(0.6 * stage_width_px);
        stage.setMinHeight(0.6 * stage_height_px);

        // The stage's width and height are initially NaN. If we don't set the width and height here, we get
        // problems hereafter. To avoid this issue, we set the width and height to arbitrary-ish initial
        // values. Actually, we use some hopefully decent values.

        stage.setWidth(0.9 * stage_width_px);
        stage.setHeight(0.9 * stage_height_px);

        stage.setResizable(true);
        stage.setResizable(false);

        stage.setFullScreen(true);
        stage.setFullScreen(false);

        // stage.centerOnScreen();
        stage.setX(screen_bounds.getMaxX() - canvas_width - 10);
        stage.setY(screen_bounds.getMinY());
    }
}