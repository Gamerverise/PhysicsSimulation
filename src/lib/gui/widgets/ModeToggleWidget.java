package gui.widgets;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;

import java_lang_extensions.multi_class.*;
import gui.multi_class.*;
import gui.layout_options.*;
import gui.widgets.VerticalListWidget;

import static java_extensions.MathX.*;
import static gui.layout_options.NativeLayoutOption.*;

public class ModeToggleWidget extends VerticalListWidget implements Descendant
{
    /***** Multi-class ****************************************/

    DescendantDispatcher dispatcher = new DescendantDispatcher(
            new _AEMBOT_GUI_Ancestor(this)
    );

    /**********************************************************/

    ToggleGroup mode;

    RadioButton keyboard_mode;
    RadioButton Xbox_mode;
    RadioButton joystick_mode;
    RadioButton semi_mode;
    RadioButton auto_mode;

    ModeToggleWidget() {
        keyboard_mode = new RadioButton("T/O Keyboard");
        Xbox_mode = new RadioButton("T/O Xbox Controller");
        joystick_mode = new RadioButton("T/O Dual Joystick");
        semi_mode = new RadioButton("Semi-autonomous");
        auto_mode = new RadioButton("Autonomous");

        double spacing = 10;

        set_entries(
                new Layout(keyboard_mode,  H_ALIGNMENT(HPos.LEFT), V_ALIGNMENT(VPos.CENTER)),
                spacing,
                new Layout(Xbox_mode,      H_ALIGNMENT(HPos.LEFT), V_ALIGNMENT(VPos.CENTER)),
                spacing,
                new Layout(joystick_mode,  H_ALIGNMENT(HPos.LEFT), V_ALIGNMENT(VPos.CENTER)),
                spacing,
                new Layout(semi_mode,      H_ALIGNMENT(HPos.LEFT), V_ALIGNMENT(VPos.CENTER)),
                spacing,
                new Layout(auto_mode,      H_ALIGNMENT(HPos.LEFT), V_ALIGNMENT(VPos.CENTER))
        );

        mode = new ToggleGroup();

        keyboard_mode.setToggleGroup(mode);
        Xbox_mode.setToggleGroup(mode);
        joystick_mode.setToggleGroup(mode);
        semi_mode.setToggleGroup(mode);
        auto_mode.setToggleGroup(mode);
    }

    protected double computePrefWidth(double height) {
        return max(
                keyboard_mode.prefWidth(height),
                Xbox_mode.prefWidth(height),
                joystick_mode.prefWidth(height),
                semi_mode.prefWidth(height),
                auto_mode.prefWidth(height)
        );
    }
}
