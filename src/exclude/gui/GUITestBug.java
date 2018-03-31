package gui;

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
import javafx.stage.Stage;

import java.io.FileNotFoundException;

//public class GUITestBug extends Application {
//
//    static Background bg;
//
//    static class ControlPanelWidgetTest extends TitledPane {
//        TilePane tiles;
//
//        ModeToggleWidget mode;
//        Button bark_button;
//        Button water_button;
//
//        MediaPlayer bark_player;
//
//        public ControlPanelWidgetTest() {
////            getStyleClass().addAll("AEMBOT", "AEMBOT_Framed_Group", "AEMBOT_ControlPanelWidget");
//            getStyleClass().addAll("yo");
//
//            setText("Controls");
//
//            tiles = new TilePane();
//            tiles.getStyleClass().addAll("AEMBOT", "AEMBOT_TilePane", "AEMBOT_ControlPanelWidget_TilePane");
//
////            tiles.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
//            bg = tiles.getBackground();
//            bg = new Background(new BackgroundFill(Color.CORNFLOWERBLUE, CornerRadii.EMPTY, Insets.EMPTY));
//
//            mode = new ModeToggleWidget();
//            bark_button = new Button("Bark!");
//            water_button = new Button("Water Challenge!");
//
//            //           Region children[] = {mode, bark_button, water_button};
//            Region children[] = {bark_button};
//            tiles.setPrefRows(children.length);
//
//            tiles.getChildren().addAll(children);
//
//            setContent(tiles);
//        }
//
//        @Override
//        protected void layoutChildren() {
//            super.layoutChildren();
//
//            layoutInArea(tiles,
//                    0, 0,
//                    getWidth(), getHeight(),
//                    0, HPos.CENTER, VPos.CENTER);
//
//            tiles.layout();
//        }
//    }
//
//    @Override
//    public void start(Stage primaryStage) {
//        BorderPane root = new BorderPane();
//        TilePane tiles = new TilePane();
//        ControlPanelWidgetTest controls = new ControlPanelWidgetTest();
//
//        root.setCenter(controls);
//
//        ToggleButton toggle = new ToggleButton("Toggle color");
//        HBox hbox = new HBox(5, toggle);
//        hbox.setAlignment(Pos.CENTER);
//        root.setBottom(hbox);
//
////        vbox.styleProperty().bind(Bindings.when(toggle.selectedProperty())
////                .then("-fx-background-color: cornflowerblue;")
////                .otherwise("-fx-background-color: white;"));
//
////        tiles.backgroundProperty().bind(Bindings.when(toggle.selectedProperty())
////                .then(new Background(new BackgroundFill(Color.CORNFLOWERBLUE, CornerRadii.EMPTY, Insets.EMPTY)))
////                .otherwise(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY))));
//
//        Scene scene = new Scene(root, 300, 250);
//
//        primaryStage.setTitle("Hello World!");
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }
//
//    public static void main(String[] args)
//            throws FileNotFoundException
//    {
//        launch(args);
//    }
//}
