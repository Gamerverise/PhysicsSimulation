import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class UI {
    double canvas_width = 1305;
    double canvas_height = 795;
    double canvas_aspect_ratio = canvas_width / canvas_height;

    Rectangle2D screen_bounds = Screen.getPrimary().getVisualBounds();

    Stage stage;
    GraphicsContext gc;

    GameWidget game_widget;

    UI(Stage stage) {
        this.stage = stage;

        Group root = new Group();

        game_widget = new GameWidget(800, 800,
                                     SolarSystem.solar_sys_game_view_sun,
                                     SolarSystem.solar_system_sim,
                                     false);

        root.getChildren().add(game_widget);
        Scene scene = new Scene(root, canvas_width, canvas_height);
        stage.setScene(scene);

        stage.setTitle("Physics SimulationDynamic");
        stage.setX(screen_bounds.getMaxX() - canvas_width);
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
            }
        });

        stage.show();

        game_widget.init_run();
    }
}
