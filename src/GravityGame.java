import gui.javafx_api_extensions.ApplicationX;
import gui.stylesheets.GravityGameStylesheets;
import gui.widgets.GravityGameWidget;
import gui.widgets.GravityGameStage;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import missions.SolarSystem;

import static gui.javafx_api_extensions.javafx_support.WinBorderStructOverloadConstants.UNKNOWN_BORDER_SIZES;
import static lib.data_structures.RunCommand.*;

public class GravityGame extends ApplicationX {
    Rectangle2D screen_bounds = Screen.getPrimary().getVisualBounds();

    GravityGameStylesheets stylesheets = new gui.stylesheets.GravityGameStylesheets();
    public static final double widget_spacing = 20;

//    double canvas_width = 1305;
//    double canvas_height = 795;

    double canvas_width = 800;
    double canvas_height = 800;

    double canvas_aspect_ratio = canvas_width / canvas_height;

    GravityGameStage stage;
    Scene scene;
    GravityGameWidget game_widget;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        build_gui(stage);

        //        System.out.print(Misc.JavaFX_node_tree_debug(stage, 0));

        stage.show();
        game_widget.init_run();
    }

    public void build_gui(Stage stage) {

        this.game_widget = new GravityGameWidget(canvas_width, canvas_height,
                SolarSystem.solar_sys_game_view_sun_earth,
                SolarSystem.solar_system_sim);

        this.scene = new Scene(game_widget);

        this.stage = new GravityGameStage(stage, scene);

        scene.getStylesheets().addAll(gui.dummy_scene_to_load_and_hold_stylesheet.getStylesheets());

        Group root = new Group();


        root.getChildren().add(game_widget);
        Scene scene = new Scene(root, canvas_width, canvas_height);
        stage.setScene(scene);

        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case SPACE:
                    game_widget.toggle_run_suspend();
                    break;
                case R:
                    game_widget.reset();
                    break;
                case LEFT:
                    game_widget.reset();
                    break;
                case RIGHT:
                    game_widget.reset();
                    break;
                case UP:
                    game_widget.reset();
                    break;
                case DOWN:
                    game_widget.reset();
                    break;
            }
        });
    }
}
