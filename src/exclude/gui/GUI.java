package gui;

import java.util.Optional;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.event.EventDispatcher;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.input.InputMethodRequests;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Transform;
import javafx.stage.Stage;

import java.io.*;
import javafx.stage.DirectoryChooser;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import java_extensions.*;
import gui.widgets.*;

public class GUI extends Application {
    public static Scene dummy_scene_to_load_and_hold_AEMBOT_stylesheet;
    public static Image map_img;
    public static Image robot_img;
    public static Media bark_mp3;

    public static final double widget_spacing = 20;

    WindowWidget window;

    @Override
    public void start(Stage stage) {

        /*

        WindowWidget                                    (no base class as yet)
            ConsoleWidget                               (extends Pane > Region > Group > Parent > Node)
                RealTimeModelWidget                     (extends Pane > Region > Group > Parent > Node)
                CameraFeedWidget                        (extends Pane > Region > Group > Parent > Node; need to integrate JavaCV)
                ControlPanelWidget                      (extends VBox > Pane > Region > Group > Parent > Node)
                    ModeToggleWidget                    (no base class as yet)
                    BarkButtonWidget                    (no base class as yet)
                DataPaneWidget                          (extends HBox > Pane > Region > Group > Parent > Node)
                    CoordinatesWidget                   (extends HBox > Pane > Region > Group > Parent > Node)
                    SpeedometerWidget                   (no base class as yet)
                    BatteryVoltageGraphWidget           (no base class as yet)
        */

        locate_and_read_data_files(stage);

        window = new WindowWidget(this, stage);
    }

    public void locate_and_read_data_files(Stage stage) {
        String perist_file_pathname = System.getProperty("user.dir") + "\\__path_to_FRC_2018_repository.txt";
        String repo_path_name = null;
        String repo_path_URI_string = null;

        try {
            BufferedReader lines = FileX.get_file_lines(perist_file_pathname);
            repo_path_name = lines.readLine();
            repo_path_URI_string = new File(repo_path_name).toURI().toString();
        } catch (FileNotFoundException e) {
            // It's okay if the persist file does not exist.
        } catch (IOException e) {
            // It's okay if the persist file can't be read.
        }

        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Directory of FRC_2018 Repository");

        while (true) {

            try {
                // There is no way to manipulate stylesheets independently of Scenes and Parents, so we gotta
                // do this dummy_scene_to_load_and_hold_AEMBOT_stylesheet shiz.

                dummy_scene_to_load_and_hold_AEMBOT_stylesheet = new Scene(new Group());

                dummy_scene_to_load_and_hold_AEMBOT_stylesheet
                        .getStylesheets().addAll(gui.widgets.window_widget_support.GravityGameStylesheets.get_URIs(repo_path_URI_string));

                map_img = new Image(repo_path_URI_string + ".idea/data_files/Map/RealTimeMap_Test.png");
                robot_img = new Image(repo_path_URI_string + ".idea/data_files/Map/Robot.png");
                bark_mp3 = new Media(repo_path_URI_string + ".idea/data_files/Sounds/bark_sound.mp3");

                Files.write(
                        Paths.get(perist_file_pathname),
                        Arrays.asList(repo_path_name),
                        Charset.forName("UTF-8")
                );

                break;
            } catch (Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                ButtonType yes_button_type = new ButtonType("Yes");
                ButtonType quit_button_type = new ButtonType("Quit");

                alert.getButtonTypes().setAll(quit_button_type, yes_button_type);

                Button yes_button = (Button)alert.getDialogPane().lookupButton(yes_button_type);
                yes_button.setDefaultButton(true);

                alert.setTitle("Invalid FRC_2018 Repository");
                alert.setHeaderText("FRC_2018 repository not found. Choose directory of repository?");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == quit_button_type)
                    System.exit(-1);

                File file = chooser.showDialog(stage);

                if (file != null) {
                    repo_path_name = file.toString() + "\\";
                    repo_path_URI_string = file.toURI().toString();
                }

                continue;
            }
        }
    }
}
