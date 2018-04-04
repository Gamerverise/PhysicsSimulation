package lib.javafx_api_extensions;

import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Screen;
import javafx.geometry.Rectangle2D;
import lib.java_lang_extensions.anonymous_types.TypedStruct;

import static lib.javafx_api_extensions.StageX.SizeChangeFlag.*;
import static lib.javafx_api_extensions.StageX.WinBorder.*;
import static lib.javafx_api_extensions.javafx_support.WinBorderStructOverloadConstants.*;
import static lib.javafx_api_extensions.javafx_support.WinSizeFlagOverloadConstants.*;
import static lib.java_lang_extensions.anonymous_types.StructField.F;
import static lib.java_lang_extensions.anonymous_types.TypedStruct.*;

public class StageX {

    public enum WinBorder {
        TOP, LEFT, BOTTOM, RIGHT
    }

    TypedStruct<WinBorder, Double> win_borders_px;

    public Stage stage;
    public Scene scene;

    @SuppressWarnings("unchecked")
    public StageX(Stage stage, UNKNOWN_BORDER_SIZES overload_constant) {
        this(stage, TS(F(TOP, -1d), F(LEFT, -1d), F(BOTTOM, -1d), F(RIGHT, -1d)));
    }

    @SuppressWarnings("unchecked")
    public StageX(Stage stage, HEURISTIC_BORDER_SIZES overload_constant) {
        this(stage, TS(
                        F(TOP, 15 * 5d),    // Heuristic for top
                        F(LEFT, 15d),       // Heuristic for left
                        F(BOTTOM, 15d),     // Heuristic for bottom
                        F(RIGHT, 15d)       // Heuristic for right
                )
        );
    }

    @SuppressWarnings("unchecked")
    public StageX(Stage stage, double top, double left, double bottom, double right) {
        this(stage, TS(F(TOP, top), F(LEFT, left), F(BOTTOM, bottom), F(RIGHT, right)));
    }

    public StageX(Stage stage,
                  TypedStruct<WinBorder, Double> win_borders_px)
    {
        this.stage = stage;

        this.win_borders_px = win_borders_px;

        // FIXME: Do we still need these listeners?

        stage.widthProperty().addListener(
                (observable, old_value, new_value) ->
                        size_changed_event_handler(WIDTH_CHANGED, observable, old_value, new_value)
        );

        stage.heightProperty().addListener(
                (observable, old_value, new_value) ->
                        size_changed_event_handler(HEIGHT_CHANGED, observable, old_value, new_value)
        );

//      On my current system, there is a slight visual effect of flickering/jumping of the window as it
//      appears when it is shown for the first time. Not sure why that is.
    }

    public void set_scene(Scene scene) {
        this.scene = scene;
        stage.setScene(scene);
    }

    enum SizeChangeFlag {WIDTH_CHANGED, HEIGHT_CHANGED}

    public void size_changed_event_handler(
            SizeChangeFlag flag,
            ObservableValue<? extends Number> observable,
            Number old_value,
            Number new_value)
    {
        // Intentionally empty
    }

    @SuppressWarnings("unchecked")
    TypedStruct<WinBorder, Double> calc_win_borders_px() {
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

        return TS(F(TOP, top), F(LEFT, left), F(BOTTOM, bottom), F(RIGHT, right));
    }

    void set_size(FULL_SCREEN overload_constant) {
        // Not finished yet
    }

    void set_size(ORIGINAL_SIZE overload_constant) {
        // Not finished yet
    }

    void set_size(MAXIMIZED overload_constant) {
        // Not finished yet
    }

    @SuppressWarnings("unused")
    void set_size(MAXIMIZED_FIXED_ASPECT_RATIO overload_constant, double aspect_ratio, boolean decorated) {
        Rectangle2D primary_screen_bounds = Screen.getPrimary().getVisualBounds();

        double primary_screen_width_px = primary_screen_bounds.getWidth();
        double primary_screen_height_px = primary_screen_bounds.getHeight();

        TypedStruct<WinBorder, Double> borders_px = calc_win_borders_px();

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

        if (aspect_ratio >= max_scene_aspect_ratio) {
            stage_width_px = primary_screen_width_px;
            double scene_width_px = stage_width_px - borders_px.get(LEFT) - borders_px.get(RIGHT);
            stage_height_px = borders_px.get(TOP) + scene_width_px / aspect_ratio + borders_px.get(BOTTOM);
        } else {
            stage_width_px = borders_px.get(LEFT) + max_scene_height_px * aspect_ratio + borders_px.get(RIGHT);
            stage_height_px = primary_screen_height_px;
        }

        // FIXME: What effect does this function have?
    }
}
