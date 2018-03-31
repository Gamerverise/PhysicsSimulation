package gui.multi_class;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.chart.Axis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ValueAxis;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.TilePane;

import java_lang_extensions.multi_class._Ancestor;

public class _Coin_GUI_Ancestor extends _Ancestor<Node> {

    public _Coin_GUI_Ancestor(Node n) {
        super(n);


        n.getStyleClass().add("Coin_Node");


        if (n instanceof Parent)
            n.getStyleClass().add("Coin_Parent");
        if (n instanceof Region)
            n.getStyleClass().add("Coin_Region");
        if (n instanceof TilePane)
            n.getStyleClass().add("Coin_TilePane");
        if (n instanceof TitledPane)
            n.getStyleClass().add("Coin_TitledPane");
        if (n instanceof Axis)
            n.getStyleClass().add("Coin_Axis");
        if (n instanceof ValueAxis)
            n.getStyleClass().add("Coin_ValueAxis");
        if (n instanceof NumberAxis)
            n.getStyleClass().add("Coin_NumberAxis");


//        if (n instanceof SomeWidget)
//            n.getStyleClass().add("SomeWidget");

//        n.getStyleClass().add("Coin");
    }


    static public Class get_class() {
        return _Coin_GUI_Ancestor.class;
    }
}
