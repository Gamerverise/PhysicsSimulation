package gui.javafx_api_extensions;

import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Screen;
import javafx.geometry.Rectangle2D;
import lib_2.java_lang_extensions.anonymous_types.Struct;

import static gui.javafx_api_extensions.StageX.SizeChangeFlag.*;
import static gui.javafx_api_extensions.StageX.WinBorder.*;
import static lib_2.java_lang_extensions.anonymous_types.StructField._;
import static gui.javafx_api_extensions.WinBorderStructOverloadConstants.*;

public class StageX {

    enum WinBorder {TOP, LEFT, BOTTOM, RIGHT}

    Struct<WinBorder, Double> win_borders_px;

    Stage stage;
    Scene scene;

    public StageX(Stage stage, Scene scene, UNKNOWN_BORDER_SIZES constant_overload) {
        this.StageX(
                stage,
                scene,
                new Struct<WinBorder, Double>(
                        _(TOP, -1d),
                        _(LEFT, -1d),
                        _(BOTTOM, -1d),
                        _(RIGHT, -1d)
                )
        );
    }

    public StageX(Stage stage, Scene scene, HEURISTIC_BORDER_SIZES constant_overload) {
        this.StageX(
                stage,
                scene,
                new Struct<WinBorder, Double>(
                        _(TOP, 15 * 5d),    // Heuristic for top
                        _(LEFT, 15d),       // Heuristic for left
                        _(BOTTOM, 15d),     // Heuristic for bottom
                        _(RIGHT, 15d)       // Heuristic for right
                )
        );
    }

    public StageX(Stage stage, Scene scene, double top, double left, double bottom, double right) {
        this.StageX(
                stage,
                scene,
                new Struct<WinBorder, Double>(
                        _(TOP, top),
                        _(LEFT, left),
                        _(BOTTOM, bottom),
                        _(RIGHT, right)
                )
        );
    }

    public StageX(Stage stage,
                  Scene scene,
                  Struct<WinBorder, Double> win_borders_px)
    {
        this.stage = stage;
        this.scene = scene;

        stage.setScene(scene);

        this.win_borders_px = win_borders_px;

        stage.widthProperty().addListener((observable, old_value, new_value) -> {
            size_changed_event_handler(WIDTH_CHANGED, observable, old_value, new_value);
        });

        stage.heightProperty().addListener((observable, old_value, new_value) -> {
            size_changed_event_handler(HEIGHT_CHANGED, observable, old_value, new_value);
        });

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

    Struct<WinBorder, Double> calc_win_borders_px() {
        double top;
        double left;
        double bottom;
        double right;

        double win_width_px = scene.getWindow().getWidth();
        double win_height_px = scene.getWindow().getHeight();

        // The stage's width and height are initially NaN. If the program reaches this point, and either
        // width or height is still NaN, we get problems hereafter. To avoid this issue, we change a NaN
        // to an arbitrary-ish initial value. Actually, we use some hopefully decent values. However, we
        // the stage's width and height early on above, before we set the width- and height-changed
        // handlers, so the checks for NaN should never give a positive result. We leave this code just
        // in case something changes or goes wrong in the future.

        if (Double.isNaN(win_width_px) || Double.isNaN(win_height_px)) {
            // We guess the approximate window decoration thicknesses:

            top = 50;
            left = 10;
            bottom = 10;
            right = 10;
        } else {
            double scene_width_px = scene.getWidth();
            double scene_height_px = scene.getHeight();

            // scene.getX() is two pixels two the right of the window border on my current system

            top = scene.getY();
            left = scene.getX();
            bottom = win_height_px - top - scene_height_px;
            right = win_width_px - left - scene_width_px;
        }

        return new Struct<WinBorder, Double>(
                _(TOP, top),
                _(LEFT, left),
                _(BOTTOM, bottom),
                _(RIGHT, right)
        );
    }

    enum WinSizeFlag {FULL_SCREEN, ORIGINAL_SIZE, MAXIMIZED_FIXED_ASPECT_RATIO, MAXIMIZED};

    void set_size(WinSizeFlag size_flag, double aspect_ratio, boolean decorated) {
        Rectangle2D primary_screen_bounds = Screen.getPrimary().getVisualBounds();

        double primary_screen_width_px = primary_screen_bounds.getWidth();
        double primary_screen_height_px = primary_screen_bounds.getHeight();

        Struct<WinBorder, Double> borders_px = calc_win_borders_px();

        double max_scene_width_px = primary_screen_width_px - borders_px.get(LEFT) - borders_px.get(RIGHT);
        double max_scene_height_px = primary_screen_height_px - borders_px.get(TOP) - borders_px.get(BOTTOM);

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

class WinBorderStructOverloadConstants {

    public static class UNKNOWN_BORDER_SIZES {
        private UNKNOWN_BORDER_SIZES() {
        }
    }

    public static class HEURISTIC_BORDER_SIZES {
        private HEURISTIC_BORDER_SIZES() {
        }
    }

    public static final UNKNOWN_BORDER_SIZES UNKNOWN_BORDER_SIZES = null;
    public static final HEURISTIC_BORDER_SIZES HEURISTIC_BORDER_SIZES = null;
}

class WinSizeFlagOverloadConstants {

    public static class FULL_SCREEN {
        private FULL_SCREEN() {}
    }
    public static class ORIGINAL_SIZE {
        private ORIGINAL_SIZE() {}
    }
    public static class MAXIMIZED_FIXED_ASPECT_RATIO {
        private MAXIMIZED_FIXED_ASPECT_RATIO() {}
    }
    public static class MAXIMIZED {
        private MAXIMIZED() {}
    }

    public static final FULL_SCREEN FULL_SCREEN = null;
    public static final ORIGINAL_SIZE ORIGINAL_SIZE = null;
    public static final MAXIMIZED_FIXED_ASPECT_RATIO MAXIMIZED_FIXED_ASPECT_RATIO = null;
    public static final MAXIMIZED MAXIMIZED = null;
}