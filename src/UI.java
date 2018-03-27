import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;

import static javafx.scene.input.KeyCode.R;
import static javafx.scene.input.KeyCode.SPACE;

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

        // FIXME
        game_widget =

        root.getChildren().add(game_widget);
        Scene scene = new Scene(root, canvas_width, canvas_height);
        stage.setScene(scene);

        stage.setTitle("Physics Simulation");
        stage.setX(screen_bounds.getMaxX() - canvas_width);
        stage.setY(screen_bounds.getMinY());
        stage.setResizable(false);

        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case SPACE:
                    game_widget.toggle_play_pause();
                    break;
                case R:
                    game_widget.reset();
                    break;
            }
        });
    }
}
