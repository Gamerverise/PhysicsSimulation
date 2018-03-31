package gui.widgets;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;

import real_time_model.RealTimeModel;
import gui.GUI;

class RealTimeModelWidget extends Pane {
    RealTimeModel model;
    Canvas map;

    RealTimeModelWidget() {
        getStyleClass().addAll("AEMBOT_RealTimeModelWidget");

        model = new RealTimeModel(GUI.map_img, GUI.robot_img);
        map = new Canvas();
    }

    protected void layoutChildren() {
        super.layoutChildren();

        map.relocate(0, 0);

        // A Canvas cannot be resized with Node.resize or Node.resizeRelocate because Canvas.isResizable returns false--
        // that is, a Canvas is not a resizable Node within the meaning of the JavaFX framework.  Instead, a Canvas
        // is resized with its class-specific sizing methods, setWidth and setHeight.

        map.setWidth(getWidth());
        map.setHeight(getHeight());
    }

    void draw() {
        GraphicsContext gc = map.getGraphicsContext2D();
        gc.drawImage(model.map.src_image, 0, 0, getWidth(), getHeight());
//        gc.drawImage(model.robot_coords.moving_object.src_image, 0, 0);
    }
}
