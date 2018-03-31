package lib.java_lang_extensions.multi_class;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.chart.Axis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ValueAxis;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.TilePane;

//import gui.widgets.*;
import lib.java_lang_extensions.multi_class._Ancestor;

public class _GravityGameGUI_Ancestor extends _Ancestor<Node> {

    public _GravityGameGUI_Ancestor(Node n) {
        super(n);


        n.getStyleClass().add("AEMBOT_Node");


        if (n instanceof Parent)
            n.getStyleClass().add("AEMBOT_Parent");
        if (n instanceof Region)
            n.getStyleClass().add("AEMBOT_Region");
        if (n instanceof TilePane)
            n.getStyleClass().add("AEMBOT_TilePane");
        if (n instanceof TitledPane)
            n.getStyleClass().add("AEMBOT_TitledPane");
        if (n instanceof Axis)
            n.getStyleClass().add("AEMBOT_Axis");
        if (n instanceof ValueAxis)
            n.getStyleClass().add("AEMBOT_ValueAxis");
        if (n instanceof NumberAxis)
            n.getStyleClass().add("AEMBOT_NumberAxis");


//        if (n instanceof ControlPanelWidget)
//            n.getStyleClass().add("AEMBOT_ControlPanelWidget");
//
//
//        if (n instanceof DataPanelWidget)
//            n.getStyleClass().add("AEMBOT_DataPanelWidget");
//
//        if (n instanceof CoordinatesWidget)
//            n.getStyleClass().add("AEMBOT_CoordinatesWidget");
//
//
//        if (n instanceof ConsoleWidget)
//            n.getStyleClass().add("AEMBOT_ConsoleWidget");


        n.getStyleClass().add("AEMBOT");
    }

    static public Class get_class() {
        return _GravityGameGUI_Ancestor.class;
    }
}
