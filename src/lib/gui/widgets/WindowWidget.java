package gui.widgets;

import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Screen;
import javafx.geometry.Rectangle2D;

import tuples.TopLeftBottomRight;
import gui.GUI;
import gui.widgets.window_widget_support.AEMBOT_Stylesheets;

import static java.lang.Double.NaN;
import static gui.widgets.WinDecorationInfo_px.Heuristic.HEURISTIC;
import static gui.widgets.WindowWidget.SizeChangeFlag.*;

public class WindowWidget {
    AEMBOT_Stylesheets stylesheets = new AEMBOT_Stylesheets();

    public GUI gui;
    public Stage stage;
    public Scene scene;
    public ConsoleWidget console;

    WinDecorationInfo_px win_decoration_info = new WinDecorationInfo_px(HEURISTIC);

    boolean first_show = true;

    public WindowWidget(GUI gui, Stage stage) {
        this.gui = gui;
        this.stage = stage;

        console = new ConsoleWidget(this);
        scene = new Scene(console);
        stage.setScene(scene);

        scene.getStylesheets().addAll(gui.dummy_scene_to_load_and_hold_AEMBOT_stylesheet.getStylesheets());

        double stage_width_px = 0.9 * Screen.getPrimary().getVisualBounds().getWidth();
        double stage_height_px = 0.9 * Screen.getPrimary().getVisualBounds().getHeight();

        stage.setTitle("AEMBOT Console -- ROBOTS DON'T QUIT!");
        stage.setMinWidth(0.6 * stage_width_px);
        stage.setMinHeight(0.6 * stage_height_px);

        // The stage's width and height are initially NaN. If we don't set the width and height here, we get
        // problems hereafter. To avoid this issue, we set the width and height to arbitrary-ish initial
        // values. Actually, we use some hopefully decent values.

        stage.setWidth(0.9 * stage_width_px);
        stage.setHeight(0.9 * stage_height_px);
        stage.centerOnScreen();

        stage.setResizable(true);
        stage.setFullScreen(true);

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

        stage.show();

//        System.out.print(Misc.JavaFX_node_tree_debug(console, 0));
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
            right = win_width_px - left - scene_height_px;
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

class WinDecorationInfo_px {

    enum Unknown {UNKNOWN};
    enum Heuristic {HEURISTIC};

    double top;
    double left;
    double bottom;
    double right;

    WinDecorationInfo_px(Unknown unknown) {
        // -1 represents that the thicknesses are unknown

        top = -1;
        left = -1;
        bottom = -1;
        right = -1;
    }

    WinDecorationInfo_px(Heuristic heuristic) {
        top = 15 * 5;   // Heuristic for top
        left = 15;      // Heuristic for left
        bottom = 15;    // Heuristic for bottom
        right = 15;     // Heuristic for right
    }

    WinDecorationInfo_px(double top, double left, double bottom, double right) {
        set(top, left, bottom, right);
    }

    boolean is_unknown() {
        return top < 0 || left < 0 || bottom < 0 || right < 0;
    }

    void set(double top, double left, double bottom, double right) {
        this.top = top;
        this.left = left;
        this.bottom = bottom;
        this.right = right;
    }
}
