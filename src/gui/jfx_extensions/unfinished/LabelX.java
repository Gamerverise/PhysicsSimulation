package gui.jfx_extensions.unfinished;

import gui.jfx_extensions.TextX;
import javafx.scene.control.Label;

import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import lib_2.java_lang_extensions.multi_class.*;

public class LabelX extends Label implements Descendant
{
    /***** Multi-class ****************************************/

    DescendantDispatcher dispatcher = new DescendantDispatcher(
            new _GravityGameGUI_Ancestor(this)
    );

    /**********************************************************/

    public <T extends _Ancestor> T dispatch() {
        return dispatcher.dispatch();
    }

    public LabelX(String label) {
        super(label);

        Font cur_font = getFont();
        setFont(Font.font(cur_font.getFamily(), FontWeight.BOLD, FontPosture.REGULAR, cur_font.getSize()));
    }

    public double get_typeset_width() {
        return new TextX(getText(), getFont()).get_typeset_width();
    }

    public double get_typeset_height() {
        return new TextX(getText(), getFont()).get_typeset_height();
    }

    protected double computePrefWidth(double height) {
        return get_typeset_width();
    }

    protected double computePrefHeight(double width) {
        return get_typeset_height();
    }
}
