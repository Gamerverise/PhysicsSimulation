package gui.jfx_extensions;

import gui.widgets.widget_support.WinDecorationInfo;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Screen;
import javafx.geometry.Rectangle2D;

import static gui.widgets.widget_support.WinDecorationInfo.Heuristic.HEURISTIC;
import static gui.jfx_extensions.StageX.SizeChangeFlag.*;

public class StageX {

    WinDecorationInfo win_decoration_info = new WinDecorationInfo(HEURISTIC);

    Stage stage;
    Scene scene;

    public StageX(Stage stage, Scene scene) {
        this.stage = stage;
        this.scene = scene;

        stage.setScene(scene);

        // FIXME (maybe fixed): What is the proper/best order of operations for initialing a window? We may have the best/proper order
        // FIXME (maybe fixed): Does stage have to be showing when centerOnScreen is called? Apparently not

        stage.widthProperty().addListener((observable, old_value, new_value) -> {
            size_changed_event_handler(WIDTH_CHANGED, observable, old_value, new_value);
        });

        stage.heightProperty().addListener((observable, old_value, new_value) -> {
            size_changed_event_handler(HEIGHT_CHANGED, observable, old_value, new_value);
        });

//      Using this explicit change listener also works, but it seems that IntelliJ cannot debug them.
//      I've left them here in order to be pedantic about how the lambdas directly about work.
//
//        class WindowSizeChangeListener implements ChangeListener<Number> {
//            SizeChangeFlag flag;
//
//            WindowSizeChangeListener(SizeChangeFlag flag) {
//                this.flag = flag;
//            }
//
//            public void changed(ObservableValue<? extends Number> observable, Number old_value, Number new_value) {
//                size_changed_event_handler(flag, observable, old_value, new_value);
//            }
//        }
//
//        stage.widthProperty().addListener(new WindowWidthSizeChangeListener(WIDTH_CHANGED));

//      On my current system, there is a slight visual effect of flickering/jumping of the window as it
//      appears when it is shown for the first time. Not sure why that is.
    }

    enum SizeChangeFlag {WIDTH_CHANGED, HEIGHT_CHANGED};

    public void size_changed_event_handler(
            SizeChangeFlag flag,
            ObservableValue<? extends Number> observable,
            Number old_value,
            Number new_value)
    {
        // Intentionally empty
    }

    TopLeftBottomRight<Double> calc_win_decoration_thicknesses_px() {
        double top;
        double left;
        double bottom;
        double right;

        double win_width_px = scene.getWindow().getWidth();
        double win_height_px = scene.getWindow().getHeight();

        double scene_width_px = scene.getWidth();
        double scene_height_px = scene.getHeight();

        // The stage's width and height are initially NaN. If the program reaches this point, and either
        // width or height is still NaN, we get problems hereafter. To avoid this issue, we change a NaN
        // to an arbitrary-ish initial value. Actually, we use some hopefully decent values. However, we
        // the stage's width and height early on above, before we set the width- and height-changed
        // handlers, so the checks for NaN should never give a positive result. We leave this code just
        // in case something changes or goes wrong in the future.

        if (Double.isNaN(win_width_px) || Double.isNaN(win_height_px)) {
            // We guess the aproximate window decoration thicknesses:

            top = 50;
            left = 10;
            bottom = 10;
            right = 10;
        } else {
            // scene.getX() is two pixels two the right of the window border on my current system

            top = scene.getY();
            left = scene.getX();
            bottom = win_height_px - top - scene_height_px;
            right = win_width_px - left - scene_width_px;
        }

        return new TopLeftBottomRight<Double>(top, left, bottom, right);
    }

    enum WinSizeFlag {FULL_SCREEN, ORIGINAL_SIZE, MAXIMIZED_FIXED_ASPECT_RATIO, MAXIMIZED};

    void set_size(WinSizeFlag size_flag, boolean decorated) {
        Rectangle2D primary_screen_bounds = Screen.getPrimary().getVisualBounds();

        double primary_screen_width_px = primary_screen_bounds.getWidth();
        double primary_screen_height_px = primary_screen_bounds.getHeight();

        TopLeftBottomRight<Double> decor_thicknesses_px = calc_win_decoration_thicknesses_px();

        double max_scene_width_px = primary_screen_width_px - decor_thicknesses_px.left - decor_thicknesses_px.right;
        double max_scene_height_px = primary_screen_height_px - decor_thicknesses_px.top - decor_thicknesses_px.bottom;

        double max_scene_aspect_ratio = max_scene_width_px / max_scene_height_px;

        // Dimensions of the ConsoleWidget, Scene, and Window (Stage)
        //
        // Here is our requirement:
        //
        //      * If flag is MAXIMIZED_FIXED_ASPECT_RATIO, the window should be as large as possible subject to the
        //        constraint that the ConsoleWidget, without the logo, should be the same size as the Scene.
        //
        // To meet this requirement, we need to know the thicknesses of the window decorations,
        // and set the window's size accordingly.
        //
        // Dimensions are Guesses
        //
        // Our scene dimensions are only guesses because in JavaFX, there is no way to determine the
        // thicknesses of the window decorations accurately. Before showing the window, JavaFX does not even
        // report the thicknesses. After showing the window, JavaFX reports wrong win_decoration_information
        // for the thicknesses, in my current system. (scene.getX() is two pixels to the right of the window
        // border.)

        double stage_width_px;
        double stage_height_px;

        if (console.aspect_ratio >= max_scene_aspect_ratio) {
            stage_width_px = primary_screen_width_px;
            double scene_width_px = stage_width_px - win_decoration_info.left - win_decoration_info.right;
            stage_height_px = win_decoration_info.top + scene_width_px / console.aspect_ratio + win_decoration_info.bottom;
        } else {
            stage_width_px = win_decoration_info.left + max_scene_height_px * console.aspect_ratio + win_decoration_info.right;
            stage_height_px = primary_screen_height_px;
        }
    }
}

