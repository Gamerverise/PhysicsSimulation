package gui.widget;

import javafx.collections.ObservableList;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import lib.java_lang_extensions.tuples.Geometry_TL_BR;
import lib.javafx_api_extensions.SceneX;
import lib.javafx_api_extensions.StageX;

import static lib.java_lang_extensions.tuples.Geometry_TL_BR.Geometry_TL_BR;

public class GravityGameStage extends StageX {

    public GravityGameStage(SceneX scene, double top, double left, double bottom, double right) {
        this(scene, Geometry_TL_BR(top, left, bottom, right));
    }

    public GravityGameStage(SceneX scene) {
        this(scene, UNKNOWN_BORDER_SIZES);
    }

    public GravityGameStage(SceneX scene, Geometry_TL_BR<Double> win_borders_px) {
        super(scene, win_borders_px);

        // FIXME (maybe fixed): What is the proper/best order of operations for initialing a window? We may have the best/proper order
        // FIXME (maybe fixed): Does stage have to be showing when centerOnScreen is called? Apparently not

        stage.setTitle("Altered States");

        // The stage's width and height are initially NaN. If we don't set the width and height here, we get
        // problems hereafter. To avoid this issue, we set the width and height to arbitrary-ish initial
        // values. Actually, we use some hopefully decent values.

//        stage.setResizable(true);
        stage.setResizable(false);

//        stage.setFullScreen(true);
        stage.setFullScreen(false);
    }

    public void init_geometry() {
        ObservableList<Screen> screen_list = (Screen.getScreens());

        int screen_index;

        // If we have more than one screen, we will use the second screen

        if (screen_list.size() > 1)
            screen_index = 1;
        else
            screen_index = 0;

        Rectangle2D screen_bounds = screen_list.get(screen_index).getVisualBounds();

        double screen_width = screen_bounds.getWidth();
        double screen_height = screen_bounds.getHeight();
        double aspect_ratio = screen_width / screen_height;

        stage.setY(0);

        if (screen_index == 1) {

            stage.setX(screen_bounds.getMinX());
            set_wh(screen_width, screen_height);

        } else {
            double wh;

            if (aspect_ratio < 1)
                wh = screen_height / 3;
            else
                wh = 0.75 * screen_height;

            stage.setX(screen_width - wh);
            set_internal_wh(wh, wh);
        }

        stage.setMinWidth(0.6 * stage.getWidth());
        stage.setMinHeight(0.6 * stage.getHeight());
    }
}
