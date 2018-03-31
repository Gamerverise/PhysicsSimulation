package gui.widgets;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Region;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import gui.jfx_extensions.*;
import java_lang_extensions.multi_class.*;
import gui.multi_class.*;
import java_lang_extensions.multi_class.*;

public class LabeledSeparatorWidget extends Region implements Descendant
{
    /***** Multi-class ****************************************/

    DescendantDispatcher dispatcher = new DescendantDispatcher(
            new _AEMBOT_GUI_Ancestor(this)
    );

    public <T extends _Ancestor> T dispatch() {
        return dispatcher.dispatch();
    }

    /**********************************************************/

    Separator pre_line;
    LabelX label;
    Separator post_line;

    double indent_px = 20;
    double spacing_px = 5;

    LabeledSeparatorWidget(String label) {
        pre_line = new Separator();
        this.label = new LabelX(label);
        post_line = new Separator();

        getChildren().addAll(pre_line, this.label, post_line);
    }

    protected double computePrefHeight(double width) {
        return label.get_typeset_height();
    }

    public double computeMinWidth(double height) {
        return spacing_px + indent_px + spacing_px
                + label.get_typeset_width()
                + spacing_px + indent_px + spacing_px;
    }

    protected void layoutChildren() {
        double width = getWidth();
        double height = getHeight();

        double pre_line_x = spacing_px;
        double pre_line_width = indent_px;

        double label_x = pre_line_x + pre_line_width + spacing_px;
        double label_width = label.get_typeset_width();

        double post_line_x = label_x + label_width + spacing_px;
        double post_line_width = width - spacing_px - post_line_x;

        layoutInArea(pre_line, pre_line_x, 0, pre_line_width, height, 0, Insets.EMPTY, true, true, HPos.LEFT, VPos.CENTER);
        layoutInArea(label, label_x, 0, label_width, height, 0, Insets.EMPTY, true, true, HPos.LEFT, VPos.CENTER);
        layoutInArea(post_line, post_line_x, 0, post_line_width, height, 0, Insets.EMPTY, true, true, HPos.RIGHT, VPos.CENTER);
    }
}
