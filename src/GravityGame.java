import gui.debug.DebugJavaFX;
import gui.stylesheets.GravityGameStylesheets;
import gui.widget.GravityGameStage;
import gui.widget.GravityGameWidget;
import lib.javafx_api_extensions.SceneX;
import lib.widget.adapters.SceneRootAdapter;
import javafx.stage.Stage;
import lib.debug.Debug;
import lib.javafx_api_extensions.ApplicationX;
import missions.SolarSystem;

import static lib.tokens.enums.RunCommand.SUSPEND;

public class GravityGame extends ApplicationX {
    String user_dir = System.getProperty("user.dir");

    GravityGameStylesheets stylesheets = new GravityGameStylesheets();
    public static final double widget_spacing = 20;

    GravityGameStage stage;
    SceneX scene;
    SceneRootAdapter scene_adapter;
    GravityGameWidget game_widget;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        build_gui(stage);

        Debug.print(false, DebugJavaFX::JavaFX_node_tree_debug, scene.getRoot(), 0);

        stage.show();
    }

    public void build_gui(Stage stage) {

        this.game_widget = new GravityGameWidget(SolarSystem.mission, SUSPEND);
        this.scene_adapter = new SceneRootAdapter(game_widget);
        this.scene = new SceneX(scene_adapter);
        this.stage = new GravityGameStage(scene);

        // FIXME: How will stylesheets interact with our JavaFX_Widget mechanism?
//        this.scene.getStylesheets().addAll(user_dir + "\\src\\gui\\stylesheets\\GravityGame.css");

        this.stage.adapt_late_init(stage);

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
