import gui.jfx_extensions.ApplicationX;
import gui.stylesheets.GravityGameStylesheets;
import gui.widgets.GameWidget;
import gui.widgets.WindowWidget;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Screen;
import javafx.stage.Stage;

import missions.SolarSystem;
import gui.jfx_extensions.ApplicationX;

public class GravityGame extends ApplicationX {

    GravityGameStylesheets stylesheets = new gui.stylesheets.GravityGameStylesheets();
    public static final double widget_spacing = 20;

//    double canvas_width = 1305;
//    double canvas_height = 795;

    double canvas_width = 800;
    double canvas_height = 800;

    double canvas_aspect_ratio = canvas_width / canvas_height;

    Rectangle2D screen_bounds = Screen.getPrimary().getVisualBounds();

//    Stage stage;
//    GraphicsContext gc;

    WindowWidget window;
    GameWidget game_widget;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        build_gui(stage);
        stage.show();
        game_widget.init_run();
    }

    public void build_gui(Stage stage) {
//        this.stage = stage;

        this.stage = stage;

        public Stage stage;
        public Scene scene;
        public ConsoleWidget console;

        console = new ConsoleWidget(this);
        scene = new Scene(console);
        stage.setScene(scene);

        scene.getStylesheets().addAll(gui.dummy_scene_to_load_and_hold_AEMBOT_stylesheet.getStylesheets());

        Group root = new Group();

        game_widget = new GameWidget(canvas_width, canvas_height,
                SolarSystem.solar_sys_game_view_sun_earth,
                SolarSystem.solar_system_sim,
                false);

        root.getChildren().add(game_widget);
        Scene scene = new Scene(root, canvas_width, canvas_height);
        stage.setScene(scene);

        stage.setTitle("Gravity Game");
        stage.setX(screen_bounds.getMaxX() - canvas_width - 10);
        stage.setY(screen_bounds.getMinY());
        stage.setResizable(false);

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
