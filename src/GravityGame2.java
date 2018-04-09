import gui.debug.DebugJavaFX;
<<<<<<< HEAD
=======
import gui.widgets.adapters.SceneAdapter;
import javafx.collections.ObservableList;
import javafx.stage.Screen;
import lib.javafx_api_extensions.ApplicationX;
>>>>>>> origin/master
import gui.stylesheets.GravityGameStylesheets;
import gui.widgets.GravityGameStage;
import gui.widgets.GravityGameWidget;
import gui.widgets.adapters.SceneAdapter;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lib.debug.Debug;
import lib.javafx_api_extensions.ApplicationX;
import lib.javafx_api_extensions.ScreenX;
import missions.SolarSystem;

import static lib.data_structures.RunCommand.SUSPEND;

public class GravityGame extends ApplicationX {
    String user_dir = System.getProperty("user.dir");

    GravityGameStylesheets stylesheets = new gui.stylesheets.GravityGameStylesheets();
    public static final double widget_spacing = 20;

    GravityGameStage stage;
    Scene scene;
    SceneAdapter root;
    GravityGameWidget game_widget;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        build_gui(stage);

        Debug.<Node, Integer>print(false, DebugJavaFX::JavaFX_node_tree_debug, scene.getRoot(), 0);

        stage.show();

        game_widget.init_graphics_context();
    }

    void set_window_geometry(Stage stage) {
<<<<<<< HEAD
//        Rectangle2D screen_bounds = ScreenX.get_screen(Integer.MAX_VALUE).getBounds();
        Rectangle2D screen_bounds = ScreenX.get_screen(0).getBounds();

        stage.setX(screen_bounds.getMaxX() - root_width - 0);
        stage.setY(screen_bounds.getMinY());

        stage.setWidth(root_width);
        stage.setHeight(root_height);

        // stage.centerOnScreen();
=======
        ObservableList<Screen> screen_list = (Screen.getScreens());

        int screen_to_use;

        // If we have more than one screen, we will use the second screen

        if (screen_list.size() >= 2)
            screen_to_use = 1;
        else
            screen_to_use = 0;

        Rectangle2D screen_bounds = ScreenX.get_screen(screen_to_use).getBounds();

        double screen_width = screen_bounds.getWidth();
        double screen_height = screen_bounds.getHeight();
        double aspect_ratio = screen_width / screen_height;

        if (screen_to_use == 1) {
            stage.setX(0);
            stage.setY(0);

            stage.setWidth(screen_width);
            stage.setHeight(screen_height);
        } else if (aspect_ratio < 1) {
            stage.setX(screen_width - (screen_height / 3));
            stage.setY(0);

            stage.setWidth(screen_height / 3);
            stage.setHeight(screen_height / 3);
        } else if (aspect_ratio >= 1) {
            stage.setX(screen_width - (screen_height * 0.75));
            stage.setY(0);

            stage.setWidth(screen_height * 0.75);
            stage.setHeight(screen_height * 0.75);
        }
>>>>>>> origin/master
    }

    public void build_gui(Stage stage) {

        this.game_widget = new GravityGameWidget(
                SolarSystem.solar_sys_game_view_sun_earth,
                SolarSystem.solar_system_sim,
                SUSPEND);
        this.root = new SceneAdapter(game_widget);
        this.scene = new Scene(root);
        this.stage = new GravityGameStage(stage);

        // FIXME: How will stylesheets interact with our JavaFX_Widget mechanism?
//        this.scene.getStylesheets().addAll(user_dir + "\\src\\gui\\stylesheets\\GravityGame.css");
        this.stage.set_scene(scene);

<<<<<<< HEAD
        set_window_geometry(Stage stage);
=======
        set_window_geometry(stage);
>>>>>>> origin/master

        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case SPACE:
                    game_widget.toggle_run_suspend();
                    break;
                case R:
                    game_widget.reset();
                    break;
//                case LEFT:
//                    game_widget.reset();
//                    break;
//                case RIGHT:
//                    game_widget.reset();
//                    break;
//                case UP:
//                    game_widget.reset();
//                    break;
//                case DOWN:
//                    game_widget.reset();
//                    break;
            }
        });
    }
}
