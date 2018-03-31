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

class NodeProperties {
    String                            accessibleHelp;
    String                            accessibleRoleDescription;
    AccessibleRole                    accessibleRole;
    String                            accessibleText;
    BlendMode                         blendMode;
    Bounds                            boundsInLocal;
    Bounds                            boundsInParent;
    CacheHint                         cacheHint;
    BooleanProperty                   cache;
    Node                              clip;
    Cursor                            cursor;
    DepthTest                         depthTest;
    ReadOnlyBooleanProperty           disabled;
    BooleanProperty                   disable;
    NodeOrientation                   effectiveNodeOrientation;
    Effect                            effect;
    EventDispatcher                   eventDispatcher;
    ReadOnlyBooleanProperty           focused;
    BooleanProperty                   focusTraversable;
    ReadOnlyBooleanProperty           hover;
    String                            id;
    InputMethodRequests               inputMethodRequests;
    ReadOnlyObjectProperty<Bounds>    layoutBounds;
    double                            layoutX;
    double                            layoutY;
    Transform                         localToParentTransform;
    Transform                         localToSceneTransform;
    BooleanProperty                   managed;
    BooleanProperty                   mouseTransparent;
    NodeOrientation                   nodeOrientation;
    double                            opacity;
    Parent                            parent;
    BooleanProperty                   pickOnBounds;
    ReadOnlyBooleanProperty           pressed;
    double                            rotate;
    Point3D                           rotationAxis;
    double                            scaleX;
    double                            scaleY;
    double                            scaleZ;
    Scene                             scene;
    String                            style;
    double                            translateX;
    double                            translateY;
    double                            translateZ;
    BooleanProperty                   visible;

    public NodeProperties(Node node) {
        accessibleHelp                 = node.getAccessibleHelp();
        accessibleRoleDescription      = node.getAccessibleRoleDescription();
        accessibleRole                 = node.getAccessibleRole();
        accessibleText                 = node.getAccessibleText();
        blendMode                      = node.getBlendMode();
        boundsInLocal                  = node.getBoundsInLocal();
        boundsInParent                 = node.getBoundsInParent();
        cacheHint                      = node.getCacheHint();
        cache                          = node.cacheProperty();
        clip                           = node.getClip();
        cursor                         = node.getCursor();
        depthTest                      = node.getDepthTest();
        disabled                       = node.disabledProperty();
        disable                        = node.disableProperty();
        effectiveNodeOrientation       = node.getEffectiveNodeOrientation();
        effect                         = node.getEffect();
        eventDispatcher                = node.getEventDispatcher();
        focused                        = node.focusedProperty();
        focusTraversable               = node.focusTraversableProperty();
        hover                          = node.hoverProperty();
        id                             = node.getId();
        inputMethodRequests            = node.getInputMethodRequests();
        layoutBounds                   = node.layoutBoundsProperty();
        layoutX                        = node.getLayoutX();
        layoutY                        = node.getLayoutY();
        localToParentTransform         = node.getLocalToParentTransform();
        localToSceneTransform          = node.getLocalToSceneTransform();
        managed                        = node.managedProperty();
        mouseTransparent               = node.mouseTransparentProperty();
        nodeOrientation                = node.getNodeOrientation();
        opacity                        = node.getOpacity();
        parent                         = node.getParent();
        pickOnBounds                   = node.pickOnBoundsProperty();
        pressed                        = node.pressedProperty();
        rotate                         = node.getRotate();
        rotationAxis                   = node.getRotationAxis();
        scaleX                         = node.getScaleX();
        scaleY                         = node.getScaleY();
        scaleZ                         = node.getScaleZ();
        scene                          = node.getScene();
        style                          = node.getStyle();
        translateX                     = node.getTranslateX();
        translateY                     = node.getTranslateY();
        translateZ                     = node.getTranslateZ();
        visible                        = node.visibleProperty();
    }
}

class ParentProperties {
    ReadOnlyBooleanProperty needsLayout;

    public ParentProperties(Parent p) {
        needsLayout = p.needsLayoutProperty();
    }
}

class RegionProperties {
    Background                 background;
    Border                     border;
    BooleanProperty            cacheShape;
    BooleanProperty            centerShape;
    double                     height;
    Insets                     insets;
    double                     maxHeight;
    double                     maxWidth;
    double                     minHeight;
    double                     minWidth;
    Insets                     opaqueInsets;
    Insets                     padding;
    double                     prefHeight;
    double                     prefWidth;
    BooleanProperty            scaleShape;
    Shape                      shape;
    BooleanProperty            snapToPixel;
    ReadOnlyDoubleProperty     width;

    public RegionProperties(Region r) {
        background     = r.getBackground();
        border         = r.getBorder();
        cacheShape     = r.cacheShapeProperty();
        centerShape    = r.centerShapeProperty();
        height         = r.getHeight();
        insets         = r.getInsets();
        maxHeight      = r.getMaxHeight();
        maxWidth       = r.getMaxWidth();
        minHeight      = r.getMinHeight();
        minWidth       = r.getMinWidth();
        opaqueInsets   = r.getOpaqueInsets();
        padding        = r.getPadding();
        prefHeight     = r.getPrefHeight();
        prefWidth      = r.getPrefWidth();
        scaleShape     = r.scaleShapeProperty();
        shape          = r.getShape();
        snapToPixel    = r.snapToPixelProperty();
        width          = r.widthProperty();
    }
}

class LinealProperties {
    NodeProperties     node_props;
    ParentProperties   parent_props;
    RegionProperties   region_props;

    public LinealProperties(Node n) {
        node_props = new NodeProperties(n);

        if (n instanceof Region) {
            Region r = (Region)n;
            parent_props = new ParentProperties(r);
            region_props = new RegionProperties(r);
            return;
        }

        if (n instanceof Parent) {
            Parent p = (Parent)n;
            parent_props = new ParentProperties(p);
            return;
        }
    }
}

class TilePaneProperties {
    Pos             alignment;
    double          hgap;
    Orientation     orientation;
    int             prefColumns;
    int             prefRows;
    double          prefTileHeight;
    double          prefTileWidth;
    Pos             tileAlignment;
    double          tileHeight;
    double          tileWidth;
    double          vgap;

    TilePaneProperties(TilePane p) {
        alignment          = p.getAlignment();
        hgap               = p.getHgap();
        orientation        = p.getOrientation();
        prefColumns        = p.getPrefColumns();
        prefRows           = p.getPrefRows();
        prefTileHeight     = p.getPrefTileHeight();
        prefTileWidth      = p.getPrefTileWidth();
        tileAlignment      = p.getTileAlignment();
        tileHeight         = p.getTileHeight();
        tileWidth          = p.getTileWidth();
        vgap               = p.getVgap();
    }
}
