package gui.widgets;

import com.sun.javafx.scene.control.skin.TitledPaneSkin;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static javafx.scene.layout.Priority.NEVER;
import static javafx.scene.paint.Color.RED;
import static javafx.scene.paint.Color.TRANSPARENT;

import gui.GUI;
import java_lang_extensions.multi_class.*;
import gui.multi_class.*;
import gui.layout_options.*;
import gui.widgets.*;

import static gui.GUI.widget_spacing;
import static gui.widgets.VerticalListWidget.touchup_spacing;
import static gui.layout_options.NativeLayoutOption.*;
import static gui.widgets.list_layout_options.ListLayoutOption.FlexSize;

public class ControlPanelWidget extends TitledPane implements Descendant
{

    /***** Multi-class ****************************************/

    DescendantDispatcher dispatcher = new DescendantDispatcher(
            new _AEMBOT_GUI_Ancestor(this)
    );

    public <T extends _Ancestor> T dispatch() {
        return dispatcher.dispatch();
    }

    /**********************************************************/

    VerticalListWidget list;

    ModeToggleWidget mode;
    Button bark_button;
    Button water_button;
    Button full_screen_button;
    Label blank;

    MediaPlayer bark_player;

    ControlPanelWidget() {
        setText("Controls");

        mode = new ModeToggleWidget();
        bark_button = new Button("Bark!");
        water_button = new Button("Water Challenge!");
        full_screen_button = new Button("Toggle Full-screen");
        blank = new Label("Intentionally blank.");

        list = new VerticalListWidget(
                widget_spacing,
                "Mode",
                widget_spacing,
                new Layout(mode, FILL_WIDTH(false)),
                touchup_spacing,
                "Commands",
                widget_spacing,
                new Layout(full_screen_button),
                touchup_spacing,
                "LOL",
                widget_spacing,
                new Layout(bark_button),
                widget_spacing,
                new Layout(water_button),
                touchup_spacing,
                "Future Features",
                new Layout(blank, FlexSize())
        );

        bark_player = new MediaPlayer(GUI.bark_mp3);

        bark_button.setOnAction((event) -> {
            bark_player.play();
        });

        full_screen_button.setOnAction((event) -> {
            Stage stage = (Stage) getScene().getWindow();
            stage.setFullScreen(!stage.isFullScreen());
        });

        setContent(list);
    }

    protected void layoutChildren() {
        super.layoutChildren();

        layoutInArea(list,
                0, 0,
                // TitledPaneSkin may have a bug where the content container is wider than TitledPane
                ((TitledPaneSkin)getSkin()).getContentContainer().getWidth(),
                ((TitledPaneSkin)getSkin()).getContentContainer().getHeight(),
                0, Insets.EMPTY, false, false, HPos.CENTER, VPos.CENTER);
    }

//    We don't need this definition anymore. I created it while experimenting with JavaFX. I'm leaving it, commented
//    out, for future reference.
//
//    RESOLVED-ish: This was probably related to the rounding-during-layout issue with GridPanes, though the
//    interaction with setBorder still seems dubious.
//
//    protected void layoutChildren() {
//
//        // BUG: Odd behavior when calling setBorder
//        //
//        // If we uncomment the statement below, the ControlPanelWidget will expand in height on window redraws.
//        //
//        //      ((TitledPaneSkin)getSkin()).getContentContainer().setBorder(new Border(new BorderStroke(Color.TRANSPARENT, BorderStrokeStyle.SOLID, new CornerRadii(0, 0, 3, 3, false), BorderStroke.DEFAULT_WIDTHS)));
//
//        super.layoutChildren();
//    }

//    We don't need this definition anymore (at the moment) because we are using setSnapToPixel for GridPanes
//    instead of fixing their size. I'm leaving it, commented out, for future reference.
//
//    protected void layoutChildren() {
//
        // FEATURE BUG: Roughly speaking, depending on its configuration, a GridPane may compute its height
        // constraints with rounding errors:
        //
        //      min_height = round(requested_height / num_rows) * num_rows
        //
        // whereas the correct calculation should be
        //
        //      min_height = requested_height
        //
        // Without code to counter this effect, the relative size of this widget may change upon resizing.
        // Turning off snap-to-pixel does the trick:
        //
        //      setSnapToPixel(false);
        //
        // Simply fixing the size of the GridPane in layoutChildren of the parent would seem to be a more
        // general solution, but doing so simply forces errors to manifest from rounding down:
        //
        //      double width = ((TitledPaneSkin)getSkin()).getContentContainer().getWidth();
        //      double height = ((TitledPaneSkin)getSkin()).getContentContainer().getHeight() - 10;
        //
        //      list.MixRegion().set_size(width, height);
//
//        super.layoutChildren();
//    }

}
