package debug;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.*;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;

import static javafx.scene.paint.Color.CORNFLOWERBLUE;
import static javafx.scene.paint.Color.RED;

public class DebugGUI extends Application {

    public static Background red_bg = new Background(new BackgroundFill(RED, null, null));
    public static Background corn_blue_bg = new Background(new BackgroundFill(CORNFLOWERBLUE, null, null));

    @Override
    public void start(Stage primaryStage) {
        ControlPanelWidget controls = new ControlPanelWidget();

        Scene scene = new Scene(controls, 300, 250);

        String repo_path_URI_string = new File("C:\\Users\\Gamerverise\\FRC_2018\\IdeaProjects\\FRC_2018\\").toURI().toString();
        scene.getStylesheets().addAll(AEMBOTStylesheets.get_URIs(repo_path_URI_string));

        primaryStage.setTitle("Debugging");
        primaryStage.setScene(scene);
        primaryStage.show();

        System.out.println("Waiting ...");
    }

    public static void main(String[] args)
        throws FileNotFoundException
    {
        launch(args);
    }
}

class ControlPanelWidget extends TitledPane {
    TilePane tiles;

//    ModeToggleWidget mode;
    Button bark_button;
    Button water_button;

    MediaPlayer bark_player;

    public ControlPanelWidget() {
        getStyleClass().setAll(
//                "AEMBOT",
//                "AEMBOT_Node",
//                "AEMBOT_Region",
//                "AEMBOT_Control",
//                "AEMBOT_Labeled",
                "AEMBOT_TitledPane",
                "AEMBOT_ControlPanelWidget"
        );

//        setBackground(new Background(new BackgroundFill(Color.RED, new CornerRadii(5), new Insets(0))));

        setText("Controls");

//        tiles = new TilePane();
//        tiles.getStyleClass().addAll("AEMBOT", "AEMBOT_TilePane", "AEMBOT_ControlPanelWidget_TilePane");

        setPrefSize(300, 250);
    }
}

class AEMBOTStylesheets {
    static String URI_relative_prefix = ".idea/src/gui/css/AEMBOT";

    static public String URI_filenames[] = {
            /****
             **** IMPORTANT! These stylesheets must be listed in order of preference desired, from
             ****            lowest precedence to highest! (Lowest precedence is most generic stylesheet, and
             ****            highest precedence is most specific stylesheet.)
             ****            and lowest precedence is most generic stylesheet.)
             ****
            */

            "Node",
            "Parent",
            "Region",

            "Pane",
            "StackPane",
            "TilePane",

            "Control",
            "Labeled",
            "TitledPane",

            "Chart",
            "LineChart",
            "XYChart",

            "Axis",
            "ValueAxis",
            "NumberAxis",

            "Shape",
            "Font",
            "Text",

            "",                  // Special case for AEMBOT.css, because it does not follow the AEMBOT_ naming convention
    };

    static String URI_suffix = ".css";

    static String[] get_URIs(String URI_absolute_prefix) {
        String URIs[] = new String[URI_filenames.length];

        int i;
        for (i = 0; i < URI_filenames.length - 1; i++) {
            URIs[i] = URI_absolute_prefix + URI_relative_prefix + "_" + URI_filenames[i] + URI_suffix;
            System.out.println("Collecting URI string: " + URIs[i]);
        }
        URIs[i] = URI_absolute_prefix + URI_relative_prefix + URI_suffix;
        System.out.println("Collecting URI string: " + URIs[i]);

        return URIs;
    }
}