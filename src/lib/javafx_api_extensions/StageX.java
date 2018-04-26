package lib.javafx_api_extensions;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;
import lib.java_lang_extensions.tuples.Geometry_TL_BR;
import lib.java_lang_extensions.tuples.WidthHeight;

import static lib.java_lang_extensions.tuples.Geometry_TL_BR.Geometry_TL_BR;
import static lib.java_lang_extensions.tuples.WidthHeight.WidthHeight;
import static lib.javafx_api_extensions.StageX.SizeChangeFlag.HEIGHT_CHANGED;
import static lib.javafx_api_extensions.StageX.SizeChangeFlag.WIDTH_CHANGED;
import static lib.javafx_api_extensions.javafx_api_extensions_support.WinSizeFlagOverloadConst.*;

public class StageX {

    public static final Geometry_TL_BR<Double> UNKNOWN_BORDER_SIZES = Geometry_TL_BR(-1d, -1d, -1d, -1d);
    public static final Geometry_TL_BR<Double> HEURISTIC_BORDER_SIZES = Geometry_TL_BR(15 * 5d, 5d, 5d, 5d);

    public enum SizeChangeFlag {WIDTH_CHANGED, HEIGHT_CHANGED}

    Geometry_TL_BR<Double> win_borders_px;
    WidthHeight<Double> win_border_allowances_px;

    public Stage stage;
    public SceneX scene;

    public StageX(SceneX scene) {
        this(scene, UNKNOWN_BORDER_SIZES);
    }

    public StageX(SceneX scene,
                  double top_border_thickness,
                  double left_border_thickness,
                  double bottom_border_thickness,
                  double right_border_thickness)
    {
        this(scene, Geometry_TL_BR(top_border_thickness, left_border_thickness, bottom_border_thickness, right_border_thickness));
    }

    public StageX(SceneX scene, Geometry_TL_BR<Double> win_borders_px) {
        this.scene = scene;
        this.win_borders_px = win_borders_px;
    }

    public void adapt_late_init(Stage stage) {
        set_underlying_stage(stage);
        calculate_win_border_allowances();
    }

    public void calculate_win_border_allowances() {
        stage.sizeToScene();

        // For this JavaFX workaround hack, we need the scene's width and height to be non-zero.
        // To satisfy this requirement, in the SceneX constructor, we default the scene's size to 100x100.

        double width_allowance = stage.getWidth() - scene.getWidth();
        double height_allowance = stage.getHeight() - scene.getHeight();

        win_border_allowances_px = WidthHeight(width_allowance, height_allowance);
    }

    public void set_underlying_stage(Stage stage) {
        this.stage = stage;
        stage.setScene(scene);

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

    public void size_changed_event_handler(
            SizeChangeFlag flag,
            ObservableValue<? extends Number> observable,
            Number old_value,
            Number new_value)
    {
        // Intentionally empty
    }

    public void set_internal_wh(double width, double height) {
        scene.set_wh(width, height);
        stage.sizeToScene();
    }

    public void set_external_wh(double width, double height) {
        double scene_width = width - win_border_allowances_px.width;
        double scene_height = height - win_border_allowances_px.height;

        scene.set_wh(scene_width, scene_height);
        stage.setWidth(width);
        stage.setHeight(height);
    }

    Geometry_TL_BR<Double> calc_win_borders_px() {
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

        return Geometry_TL_BR(top, left, bottom, right);
    }

    void set_size(FULL_SCREEN overload_const) {
        // Not finished yet
    }

    void set_size(ORIGINAL_SIZE overload_const) {
        // Not finished yet
    }

    void set_size(MAXIMIZED overload_const) {
        // Not finished yet
    }

    @SuppressWarnings("unused")
    void set_size(MAXIMIZED_FIXED_ASPECT_RATIO overload_const, double aspect_ratio, boolean decorated) {
        Rectangle2D primary_screen_bounds = Screen.getPrimary().getVisualBounds();

        double primary_screen_width_px = primary_screen_bounds.getWidth();
        double primary_screen_height_px = primary_screen_bounds.getHeight();

        Geometry_TL_BR<Double> borders_px = calc_win_borders_px();

        double max_scene_width_px = primary_screen_width_px - borders_px.left - borders_px.right;
        double max_scene_height_px = primary_screen_height_px - borders_px.top - borders_px.bottom;

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
            double scene_width_px = stage_width_px - borders_px.left - borders_px.right;
            stage_height_px = borders_px.top + scene_width_px / aspect_ratio + borders_px.bottom;
        } else {
            stage_width_px = borders_px.left + max_scene_height_px * aspect_ratio + borders_px.right;
            stage_height_px = primary_screen_height_px;
        }

        // FIXME: HIGH PRIORITY: What effect does this function have?
    }
}
