import gui.debug.DebugJavaFX;
import gui.widgets.adapters.SceneAdapter;
import javafx.scene.Parent;
import javafx.scene.layout.Region;
import lib.javafx_api_extensions.ApplicationX;
import gui.stylesheets.GravityGameStylesheets;
import gui.widgets.GravityGameWidget;
import gui.widgets.GravityGameStage;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import lib.debug.*;
import lib.javafx_api_extensions.ScreenX;
import missions.SolarSystem;

import static lib.data_structures.RunCommand.*;

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

    public void build_gui(Stage stage) {

        //    double canvas_width = 1305;
        //    double canvas_height = 795;

        double root_width = 600;
        double root_height = 600;

        this.game_widget = new GravityGameWidget(root_width, root_height,
                SolarSystem.solar_sys_game_view_sun_earth,
                SolarSystem.solar_system_sim,
                SUSPEND);
        this.root = new SceneAdapter(game_widget);
        this.scene = new Scene(root);
        this.stage = new GravityGameStage(stage);

        // FIXME: How will stylesheets interact with our JavaFX_Widget mechanism?
//        this.scene.getStylesheets().addAll(user_dir + "\\src\\gui\\stylesheets\\GravityGame.css");
        this.stage.set_scene(scene);

//        Rectangle2D screen_bounds = ScreenX.get_screen(Integer.MAX_VALUE).getBounds();
        Rectangle2D screen_bounds = ScreenX.get_screen(0).getBounds();

        stage.setX(screen_bounds.getMaxX() - root_width - 0);
        stage.setY(screen_bounds.getMinY());

        stage.setWidth(root_width);
        stage.setHeight(root_height);

        // stage.centerOnScreen();

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
